package chunmaru.ua.utils

import com.google.api.client.http.ByteArrayContent
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.File

class GoogleDriveUploader(private val driveService: Drive) : GoogleDriveApiService {

    override fun uploadImage(imageBytes: ByteArray, fileName: String, mimeType: String): String {
        val fileMetadata = File().apply {
            name = fileName
            this.mimeType = mimeType
        }

        val mediaContent = ByteArrayContent.fromString(mimeType, imageBytes.toString(Charsets.UTF_8))

        val uploadedFile = driveService.files().create(fileMetadata, mediaContent).execute()

        println("File uploaded: ${uploadedFile.name} (${uploadedFile.id})")

        return getFileUrl(uploadedFile.id)
    }

    private fun getFileUrl(fileId: String): String {
        val file = driveService.files().get(fileId).execute()
        return file.webContentLink
    }
}