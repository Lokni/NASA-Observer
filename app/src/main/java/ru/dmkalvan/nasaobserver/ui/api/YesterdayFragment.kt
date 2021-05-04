package ru.dmkalvan.nasaobserver.ui.api

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.fragment_today.*
import kotlinx.android.synthetic.main.loading_layout.*
import ru.dmkalvan.nasaobserver.R
import ru.dmkalvan.nasaobserver.data.Days
import ru.dmkalvan.nasaobserver.data.PictureOfTheDayData
import ru.dmkalvan.nasaobserver.ui.bottomsheets.BottomNavigationDrawlerFragment
import ru.dmkalvan.nasaobserver.ui.settings.SettingsFragment
import ru.dmkalvan.nasaobserver.util.getDateFromEnum
import ru.dmkalvan.nasaobserver.viewmodel.PictureOfTheDayViewModel


class YesterdayFragment : Fragment() {


    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yesterday, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getYesterdayPicture(getDateFromEnum(Days.YESTERDAY))
            .observe(viewLifecycleOwner, { renderData(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                main_today.visibility = View.VISIBLE
                loadingLayout.visibility = View.GONE

                val serverResponseData = data.serverResponseData
                val url =
                    if (serverResponseData.mediaType == "image") serverResponseData.url else serverResponseData.thumbnailUrl
                if (url.isNullOrEmpty()) {
                    toast("Link is empty")
                } else {
                    image_view.load(url) {
                        lifecycle(viewLifecycleOwner)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    setDescriptionInBottomSheet(
                        serverResponseData.title.toString(),
                        serverResponseData.explanation.toString()
                    )

                }
            }

            is PictureOfTheDayData.Loading -> {
                main_today.visibility = View.GONE
                loadingLayout.visibility = View.VISIBLE
            }

            is PictureOfTheDayData.Error -> {
                toast(data.error.message)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> toast("Favourite")

            R.id.app_bar_settings -> {
                activity
                    ?.supportFragmentManager
                    ?.beginTransaction()
                    ?.add(R.id.container, SettingsFragment())
                    ?.addToBackStack(null)
                    ?.commitAllowingStateLoss()
            }

            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawlerFragment().show(it.supportFragmentManager, "tag")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setDescriptionInBottomSheet(header: String, body: String) {
        bottom_sheet_description_header.text = header
        bottom_sheet_description.text = body
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }
}