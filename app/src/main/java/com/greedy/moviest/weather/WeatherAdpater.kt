package com.greedy.moviest.weather


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.moviest.R
import com.greedy.moviest.databinding.ListItemWeatherBinding

class WeatherAdapter () : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    var items : Array<ModelWeather> = arrayOf()

    // 뷰 홀더 만들어서 반환, 뷰의 레이아웃은 list_item_weather.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemWeatherBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    // 전달받은 위치의 아이템 연결
    override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    // 아이템 갯수 리턴
    override fun getItemCount() : Int {
        return items?.size ?: 0

    }

    // 뷰 홀더 설정
    inner class ViewHolder(val binding: ListItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(item : ModelWeather) {
            val imgWeather = binding.imgWeather      // 날씨 이미지
            val tvTime = binding.tvTime          // 시각
            val tvHumidity =  binding.tvHumidity      // 습도
            val tvTemp =  binding.tvTemp          // 온도
            val tvRecommends = binding.tvRecommends   // 영화 추천
            val imgGenre = binding.imgGenre   // 영화 추천

            imgWeather.setImageResource(getRainImage(item.rainType, item.sky))
            tvTime.text = getTime(item.fcstTime)
            tvHumidity.text = item.humidity + "%"
            tvTemp.text = item.temp + "°"
            tvRecommends.text = getRecommends(item.temp.toInt())
            imgGenre.setImageResource(getRecommendsImg(item.temp.toInt()))
        }
    }

    fun getTime(factTime: String): String {
        if (factTime != "지금") {
            var hourSystem: Int = factTime.toInt()
            var hourSystemString = ""


            if (hourSystem == 0) {
                return "오전 12시"
            } else if (hourSystem > 2100) {
                hourSystem -= 1200
                hourSystemString = hourSystem.toString()
                return "오후 ${hourSystemString[0]}${hourSystemString[1]}시"


            } else if (hourSystem == 1200) {
                return "오후 12시"
            } else if (hourSystem > 1200) {
                hourSystem -= 1200
                hourSystemString = hourSystem.toString()
                return "오후 ${hourSystemString[0]}시"

            } else if (hourSystem >= 1000) {
                hourSystemString = hourSystem.toString()

                return "오전 ${hourSystemString[0]}${hourSystemString[1]}시"
            } else {

                hourSystemString = hourSystem.toString()

                return "오전 ${hourSystemString[0]}시"

            }

        } else {
            return factTime
        }


    }

    // 강수 형태
    fun getRainImage(rainType: String, sky: String): Int {
        return when (rainType) {
            "0" -> getWeatherImage(sky)
            "1" -> R.drawable.rainy
            "2" -> R.drawable.hail
            "3" -> R.drawable.snowy
            "4" -> R.drawable.brash
            else -> getWeatherImage(sky)
        }
    }

    fun getWeatherImage(sky: String): Int {
        // 하늘 상태
        return when (sky) {
            "1" -> R.drawable.sun                       // 맑음
            "3" -> R.drawable.cloudy                     // 구름 많음
            "4" -> R.drawable.blur                 // 흐림
            else -> R.drawable.ic_launcher_foreground   // 오류
        }
    }

}

    // 영화 추천
    fun getRecommends(temp : Int) : String{
        return when (temp) {
            in 5..8 -> "   로맨스"
            in 9..11 -> "    액션"
            in 12..16 -> "    추리"
            in 17..19 -> "   판타지"
            in 20..22 -> "   뮤지컬"
            in 23..27 -> "   스릴러"
            in 28..50 -> "    공포"
            else -> "   드라마"
        }

    }

    fun getRecommendsImg(temp : Int) : Int {
        return when (temp) {
            in 5..8 -> R.drawable.hearts
            in 9..11 -> R.drawable.gun
            in 12..16 -> R.drawable.detective
            in 17..19 -> R.drawable.hat
            in 20..22 -> R.drawable.music
            in 23..27 -> R.drawable.book
            in 28..50 -> R.drawable.ghost
            else -> R.drawable.drama
        }
    }
