package tech.mohamedashraf.presentation.utils

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MimeTypes
import androidx.media3.common.util.UnstableApi
import androidx.media3.transformer.Composition
import androidx.media3.transformer.DefaultEncoderFactory
import androidx.media3.transformer.ExportException
import androidx.media3.transformer.ExportResult
import androidx.media3.transformer.Transformer
import androidx.media3.transformer.VideoEncoderSettings
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.io.File
import kotlin.time.Instant

fun Double.format(): String = String.format("%.2f", this)

private fun localDateTime(dateTime: Long): LocalDateTime {
    val instant: Instant = Instant.fromEpochMilliseconds(dateTime)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault())
}

fun Long.date(): String {
    val localDateTime = localDateTime(this)
    var hour: String
    var part: String
    if (localDateTime.hour >= 12) {
        hour = if (localDateTime.hour-12 < 10) {
            "0${localDateTime.hour-12}"
        } else {
            "${localDateTime.hour-12}"
        }
        part = "PM"
    } else if (localDateTime.hour == 12) {
        hour = "${localDateTime.hour}"
        part = "PM"
    } else if (localDateTime.hour == 0) {
        hour = "12"
        part = "AM"
    } else {
        hour = if (localDateTime.hour < 10) {
            "0${localDateTime.hour}"
        } else {
            "${localDateTime.hour}"
        }
        part = "AM"
    }
    var minute = "${localDateTime.minute}"
    if (localDateTime.minute < 10) {
        minute = "0${localDateTime.minute}"
    }
    val date = "${if (localDateTime.day < 10) "0${localDateTime.day}" else "${localDateTime.day}"}, ${localDateTime.month.name.substring(0, 3)} ${localDateTime.year}"
    return "$date  $hour:$minute $part"
}

@OptIn(UnstableApi::class)
fun File.compressVideo(
    context: Context,
    outputFile: File,
    onSuccess: (File) -> Unit,
    onError: (Exception) -> Unit
) {
    val videoEncoderSettings = VideoEncoderSettings.Builder().setBitrate(3_500_000).build()

    val transformer = Transformer.Builder(context)
        .setVideoMimeType(MimeTypes.VIDEO_H264)
        .setEncoderFactory(DefaultEncoderFactory.Builder(context).setRequestedVideoEncoderSettings(videoEncoderSettings).build())
        .addListener(object : Transformer.Listener {
            override fun onCompleted(composition: Composition, exportResult: ExportResult) {
                onSuccess(outputFile)
            }

            override fun onError(composition: Composition, exportResult: ExportResult, exportException: ExportException) {
                onError(exportException)
            }
        })
        .build()

    transformer.start(androidx.media3.common.MediaItem.fromUri(this.toUri()), outputFile.absolutePath)
}