package tech.mohamedashraf.domain.error

class NoSalesToDownloadError(val error: String) : Exception(error)