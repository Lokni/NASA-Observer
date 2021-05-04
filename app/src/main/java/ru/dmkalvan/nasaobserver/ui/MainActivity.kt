package ru.dmkalvan.nasaobserver.ui
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.dmkalvan.nasaobserver.R
import ru.dmkalvan.nasaobserver.ui.picture.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNowAllowingStateLoss()
        }
    }
}