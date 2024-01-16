package chunmaru.ua.utils


interface GoogleDriveApiService {
    fun uploadImage(imageBytes: ByteArray, fileName: String, mimeType: String): String
}