package ru.dmkalvan.nasaobserver.data

sealed class PictureOfTheDayData{
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    object Loading: PictureOfTheDayData()
    data class Error(val error: Throwable): PictureOfTheDayData()
}
