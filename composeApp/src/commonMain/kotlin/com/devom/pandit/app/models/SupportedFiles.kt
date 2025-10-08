package com.devom.pandit.app.models

enum class SupportedFiles(
    val document: String,
    val type: String,
    val mimeTypes: List<String>
) {
    AADHAAR_CARD("Aadhaar Card", "aadhaar_card", listOf("application/pdf", "image/jpeg", "image/png")),
    PAN_CARD("Pan Card", "pan_card", listOf("application/pdf", "image/jpeg")),
    CERTIFICATE("Certificate", "certificate", listOf("application/pdf")),
    IMAGE("Image", "image", listOf("image/jpeg", "image/png", "image/gif")),
    VIDEO("Video", "video", listOf("video/mp4", "video/x-matroska", "video/webm")),
    IMAGE_AND_VIDEO("image and video", "other", listOf("image/jpeg", "image/png", "video/mp4", "video/x-matroska")),
    VIEW("View", "view", listOf("*/*")),
    OTHER("Other", "other", listOf("*/*"));
}
