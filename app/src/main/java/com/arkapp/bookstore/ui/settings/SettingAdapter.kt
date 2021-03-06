package com.arkapp.bookstore.ui.settings

import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.arkapp.bookstore.R
import com.arkapp.bookstore.data.preferences.BOOK_TYPE_BEST_SELLER
import com.arkapp.bookstore.data.preferences.BOOK_TYPE_MOST_SEARCHED
import com.arkapp.bookstore.data.preferences.BOOK_TYPE_NEW_ARRIVAL
import com.arkapp.bookstore.data.repository.PrefRepository
import com.arkapp.bookstore.utils.loadImage
import com.arkapp.bookstore.utils.showAlertDialog

/**
 * Created by Abdul Rehman on 28-02-2020.
 * Contact email - abdulrehman0796@gmail.com
 */

class SettingAdapter(
    private val context: Activity,
    private val titles: Array<String>,
    private val icons: Array<Int>,
    private val prefRepository: PrefRepository,
    private val navController: NavController
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SettingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rv_setting,
                parent,
                false
            )
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as SettingViewHolder).viewBinding

        val bookData = titles[position]
        binding.title.text = bookData
        binding.settingIcon.loadImage(icons[position])

        binding.parent.setOnClickListener {
            when (position) {
                0 -> {
                    prefRepository.setBookSearchType(BOOK_TYPE_MOST_SEARCHED)
                    navController.navigate(R.id.action_settingFragment_to_bookTypeFragment)
                }
                1 -> {
                    prefRepository.setBookSearchType(BOOK_TYPE_BEST_SELLER)
                    navController.navigate(R.id.action_settingFragment_to_bookTypeFragment)
                }
                2 -> {
                    prefRepository.setBookSearchType(BOOK_TYPE_NEW_ARRIVAL)
                    navController.navigate(R.id.action_settingFragment_to_bookTypeFragment)
                }
                3 -> {
                    context.showAlertDialog(
                        "Logout",
                        "Do you want to logout?",
                        "Logout",
                        "Cancel",
                        DialogInterface.OnClickListener { dialog, _ ->
                            prefRepository.setLoggedIn(false)
                            navController.navigate(R.id.action_settingFragment_to_splashFragment)
                            dialog.dismiss()
                        }
                    )
                }
            }
        }
    }


    override fun getItemCount() = titles.size

    override fun getItemId(position: Int): Long {
        return titles[position].hashCode().toLong()
    }

}