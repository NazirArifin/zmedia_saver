package id.zettamedia.zmedia_saver

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.AsyncTask
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ZmediaSaverPlugin(private val registrar: Registrar): MethodCallHandler {
  companion object {
    @JvmStatic
    fun registerWith(registrar: Registrar) {
      val channel = MethodChannel(registrar.messenger(), "zmedia_saver")
      channel.setMethodCallHandler(ZmediaSaverPlugin(registrar))
    }
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if(call.method == "saveToLibrary") {
      val dir = call.argument<String>("directory")
      val file = call.argument<String>("file")
      val ext = call.argument<String>("ext")
      val r: Boolean = ProcessImage(dir, file, ext, registrar).execute().get()
      result.success(r)
    } else {
      result.notImplemented()
    }
  }
}

class ProcessImage(
        private val dir: String?,
        private val file: String?,
        private val ext: String?,
        private val registrar: Registrar
): AsyncTask<Void, Void, Boolean>() {
  override fun doInBackground(vararg params: Void?): Boolean {
    try {
      val imageFile = File(dir, file)
      val imageUri = Uri.fromFile(imageFile)
      var bitmap: Bitmap = BitmapFactory.decodeFile(imageUri.path)
      val exif = ExifInterface(imageFile.absolutePath)
      val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
      if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
        bitmap = rotateBitmap(bitmap, 90)
      }
      if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
        bitmap = rotateBitmap(bitmap, 180)
      }
      if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
        bitmap = rotateBitmap(bitmap, 270)
      }
      imageFile.delete()
      val out = FileOutputStream(imageFile)
      if (ext == "png") {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
      } else {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
      }
      out.flush()
      out.close()

      val context = registrar.activeContext().applicationContext
      val nImageFile = File(dir, file)
      context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(nImageFile)))
      return true
    } catch (e: IOException) {
      e.printStackTrace()
      return false
    }
  }

  private fun rotateBitmap(bitmap: Bitmap, degrees: Int): Bitmap {
    val matrix = Matrix()
    matrix.postRotate(degrees.toFloat())
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
  }
}