package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Helper

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import androidx.annotation.Nullable
import androidx.annotation.WorkerThread
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream


object FileUtils {
    private const val TAG = "FileUtils"
    @WorkerThread
    @Nullable
    fun getReadablePathFromUri(context: Context, uri: Uri): String? {
        var path: String? = null
        if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            path = uri.getPath()
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            path = getPath(context, uri)
        }
        if (TextUtils.isEmpty(path)) {
            return path
        }
        Log.d(TAG, "get path from uri: $path")
        if (!isReadablePath(path)) {
            val index = path!!.lastIndexOf("/")
            val name = path.substring(index + 1)
            val dstPath: String =
                context.getCacheDir().getAbsolutePath() + File.separator.toString() + name
            if (copyFile(context, uri, dstPath)) {
                path = dstPath
                Log.d(TAG, "copy file success: $path")
            } else {
                Log.d(TAG, "copy file fail!")
            }
        }
        return path
    }

    fun getPath(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                Log.d("External Storage", docId)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                val dstPath: String = context.getCacheDir()
                    .getAbsolutePath() + File.separator.toString() + getFileName(context, uri)
                if (copyFile(context, uri, dstPath)) {
                    Log.d(TAG, "copy file success: $dstPath")
                    return dstPath
                } else {
                    Log.d(TAG, "copy file fail!")
                }
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.getScheme(), ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.getScheme(), ignoreCase = true)) {
            return uri.getPath()
        }
        return null
    }

    fun getFileName(context: Context, uri: Uri?): String {
        val cursor: Cursor? = context.getContentResolver().query(uri!!, null, null, null, null)
        val nameindex: Int = cursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        return cursor.getString(nameindex)
    }

    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)
        try {
            cursor = context.getContentResolver().query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val column_index: Int = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } finally {
            if (cursor != null) cursor.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.getAuthority()
    }

    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.getAuthority()
    }

    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.getAuthority()
    }

    private fun isReadablePath(@Nullable path: String?): Boolean {
        if (TextUtils.isEmpty(path)) {
            return false
        }
        val isLocalPath: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isLocalPath = if (!TextUtils.isEmpty(path)) {
                val localFile = File(path)
                localFile.exists() && localFile.canRead()
            } else {
                false
            }
        } else {
            isLocalPath = path!!.startsWith(File.separator)
        }
        return isLocalPath
    }

    private fun copyFile(context: Context, uri: Uri, dstPath: String): Boolean {
        var inputStream: InputStream? = null
        var outputStream: OutputStream? = null
        try {
            inputStream = context.getContentResolver().openInputStream(uri)
            outputStream = FileOutputStream(dstPath)
            val buff = ByteArray(100 * 1024)
            var len: Int
            while (inputStream!!.read(buff).also { len = it } != -1) {
                outputStream.write(buff, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return true
    }
}