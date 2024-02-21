package com.sudo_pacman.bookapp.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.storage
import com.sudo_pacman.bookapp.data.BookData
import com.sudo_pacman.bookapp.data.UploadData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor() {

    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private var uploadTask: UploadTask? = null


    fun downloadFile(path: File, bookName: String): Flow<Result<File>> = callbackFlow {
        storageRef.child("books/$bookName.pdf")
            .getFile(path)
            .addOnSuccessListener {
                trySend(Result.success(path))
            }
            .addOnProgressListener {
                Log.d("TTT", "downloading ${it.bytesTransferred * 100 / it.totalByteCount}")
            }
            .addOnFailureListener {
                trySend(Result.failure(Throwable(it.message)))
            }

        awaitClose()
    }


    fun getImages(): Flow<Result<List<BookData>>> = callbackFlow {
        storageRef.child("images/").listAll()
            .addOnSuccessListener { listResult ->
                val images = ArrayList<BookData>()
                var size = listResult.items.size
                listResult.items.forEach { it ->

                    val data = it.name.split("$")
                    it.getBytes(10 * 1024 * 1024).addOnSuccessListener {
                        val matrix = BitmapFactory.decodeByteArray(it, 0, it.size)

                        images.add(BookData(data[0], data[1], data[2], matrix))
                        size--

                        if (size == 0) trySend(Result.success(images))
                    }.addOnFailureListener {
                        trySend(Result.failure(it))
                    }
                }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }


//    fun uploadFile(uri: Uri): Flow<Result<Unit>> = callbackFlow {
//        storageRef.child("books/test_${UUID.randomUUID()}")
//            .putFile(uri)
//            .addOnSuccessListener {
//                trySend(Result.success(Unit))
//            }
//            .addOnFailureListener {
//                trySend(Result.failure(Throwable(it)))
//            }
//
//        awaitClose()
//    }
//
//
//    fun getImagePath() : Flow<Result<Bitmap>>  = callbackFlow{
//        storageRef.child("books/test_6731c524-9b74-4dfe-a2e0-dadf72f842f0")
//            .getBytes(1024 * 1024 * 5)
//            .addOnSuccessListener {
//                val matrix = BitmapFactory.decodeByteArray(it, 0, it.size)
//                trySend(Result.success(matrix))
//            }
//            .addOnFailureListener {
//                trySend(Result.failure(Throwable(it)))
//            }
//
//        awaitClose()
//    }


//    fun uploadFileWithProgresses(uri: Uri): Flow<UploadData> = callbackFlow {
//        uploadTask = storageRef.child("books/test_${UUID.randomUUID()}")
//            .putFile(uri)
//
//        uploadTask!!
//            .addOnSuccessListener {
//                trySend(UploadData.SUCCESS)
//            }
//            .addOnProgressListener {
//                trySend(UploadData.Progress(it.bytesTransferred, it.totalByteCount))
//            }
//            .addOnPausedListener {
//                trySend(UploadData.PAUSE)
//            }
//            .addOnCanceledListener {
//                trySend(UploadData.CANCEL)
//            }
//            .addOnFailureListener {
//                trySend(UploadData.Error(it.message ?: "Unknown error"))
//            }
//
//
//        awaitClose()
//    }
}