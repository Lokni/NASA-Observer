package ru.dmkalvan.nasaobserver.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.dmkalvan.nasaobserver.BuildConfig
import ru.dmkalvan.nasaobserver.data.PODRetrofitImpl
import ru.dmkalvan.nasaobserver.data.PODServerResponseData
import ru.dmkalvan.nasaobserver.data.PictureOfTheDayData


class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }
    fun getYesterdayPicture(dayString: String): LiveData<PictureOfTheDayData> {
        sendDateServerRequest(dayString)
        return liveDataForViewToObserve
    }
    fun getRandomImage(count: Int): LiveData<PictureOfTheDayData>{
        sendRandomImageRequest(count)
        return liveDataForViewToObserve
    }

    private fun sendRandomImageRequest(count: Int) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading

        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            PictureOfTheDayData.Error(Throwable("You need api key"))
        } else {
            retrofitImpl.getRetrofitImpl().getRandomPictureOfTheDay(apiKey, count).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        liveDataForViewToObserve.value = PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()){
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }

            })
        }
    }

    private fun sendDateServerRequest(dayString: String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading

        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()){
            PictureOfTheDayData.Error(Throwable("You need api key"))
        } else {
            retrofitImpl.getRetrofitImpl().getYesterdayPictureOfTheDay(apiKey, dayString).enqueue(object :
            Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null){
                        liveDataForViewToObserve.value = PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()){
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }

            })
        }
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading

        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need api key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }


    }

}
