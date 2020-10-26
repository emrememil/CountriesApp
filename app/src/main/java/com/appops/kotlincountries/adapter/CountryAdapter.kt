package com.appops.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.appops.kotlincountries.R
import com.appops.kotlincountries.databinding.ItemCountryBinding
import com.appops.kotlincountries.model.Country
import com.appops.kotlincountries.util.downloadFromUrl
import com.appops.kotlincountries.util.placeHolderProgressBar
import com.appops.kotlincountries.view.FeedFragmentDirections
import kotlinx.android.synthetic.main.item_country.view.*

class CountryAdapter(val countryList : ArrayList<Country>):
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {


    class CountryViewHolder(var view: ItemCountryBinding) : RecyclerView.ViewHolder(view.root){
        // CountryViewHolder constructor'ı içerisinde oluşturduğumuz view,  RecyclerView.ViewHolder(view) 'a paslanacak.
        //Bu şekilde bütün işlemleri birbirine bağlamayı başarıyoruz.

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        //Burada oluşturduğumuz layout ile burdaki adapteri birbirine bağlıyoruz.
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_country,parent,false)
        val view = DataBindingUtil.inflate<ItemCountryBinding>(inflater,R.layout.item_country,parent,false)
        return  CountryViewHolder(view)  //Burada verdiğimiz view parametresi yukardaki sınıfın constructorına giriyor

    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.view.country = countryList[position]
        holder.view.listener = this

        /*
        holder.view.name.text = countryList[position].countryName
        holder.view.region.text = countryList[position].countryRegion
        holder.view.imageView.downloadFromUrl(countryList[position].flagUrl, placeHolderProgressBar(holder.view.context))

        holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
         */
    }

    fun updateCountryList(newCountryList: List<Country>){ //Ui'da Swipe Refresh Layout çalıştığında yani sayfa yenilendiğinde
        // bu durumu adapter sınıfına haber verip recyclerview'i güncellememiz gerekiyor. Ondan dolayı bu fonk. oluşturuldu.
        countryList.clear()     //Eski liste siliniyor.
        countryList.addAll(newCountryList)      //Yeni liste ekleniyor.
        notifyDataSetChanged()      //Yapılan bu değişikliklerin geçerli olması için adapteri yenilemek gerek. Ondan dolayı bu methodu kullandık.
    }

    override fun onCountryClicked(v: View) {
        val uuid = v.countryUuidText.text.toString().toInt()
        val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}