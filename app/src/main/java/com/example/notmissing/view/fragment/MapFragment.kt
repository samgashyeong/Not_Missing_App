package com.example.notmissing.view.fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notmissing.R
import com.example.notmissing.adapter.MapAdater
import com.example.notmissing.api.NotMissingServerClient
import com.example.notmissing.databinding.FragmentMapBinding
import com.example.notmissing.model.missingpeople.People
import com.example.notmissing.model.safespot.SafeSpotModel
import com.example.notmissing.model.safespot.Spot
import com.example.notmissing.view.MainActivity
import com.example.notmissing.viewmodel.MainViewModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapPOIItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapFragment : Fragment() {
    private val vm : MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    private lateinit var mapView : MapView
    private lateinit var binding: FragmentMapBinding
    private lateinit var marker : MapPOIItem
    private var onClickItem : Spot? = null
    private var isLoding : Boolean = false
    private var pageIndex : Int = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_map, container, false)

        binding.slidLayout.addPanelSlideListener(PanelEventListener())
        mapView = MapView(requireActivity())
        binding.callBtn.hide()

        val mapViewContainer = binding.mapView as ViewGroup
        mapViewContainer.addView(mapView)

        binding.recycler.addOnScrollListener(object :  RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if(!isLoding){
                    if((recyclerView.layoutManager as LinearLayoutManager?)!!.findLastVisibleItemPosition() == vm.mapList.value!!.size-1){
                        getData()
                        this@MapFragment.isLoding = true
                    }
                }
            }
        })

        vm.mapList.observe(viewLifecycleOwner, {
            binding.recycler.adapter = MapAdater(vm.mapList.value as ArrayList<Spot>){position: Int, data: Spot ->
                //맵 관련 코드 작성
                binding.slidLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
                onClickItem = data
                binding.callBtn.show()
                setMap(data)
            }

        })
        binding.callBtn.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("tel:${onClickItem!!.telno}")))
        }

        return binding.root
    }

    private fun getData() {
        CoroutineScope(Dispatchers.IO).launch {
            NotMissingServerClient.getApiService().getSafeSpot(pageIndex.toString()).enqueue(object :
                Callback<SafeSpotModel> {
                override fun onResponse(
                    call: Call<SafeSpotModel>,
                    response: Response<SafeSpotModel>
                ) {
                    Log.d(TAG, "onResponse: ${response}")
                    if(response.isSuccessful){
                        vm.mapList.value!!.removeLast()
                        val list = response.body()!!.list as ArrayList<Spot>
                        list.add(Spot(" "," "," "," "," "," ",1,1.0,1.0,1," "," "," "," "))
                        vm.mapList.value!!.addAll(list)
                        binding.recycler.adapter!!.notifyItemInserted(vm.mapList.value!!.lastIndex)
                        isLoding = false
                        pageIndex++
                    }
                }

                override fun onFailure(call: Call<SafeSpotModel>, t: Throwable) {
                    t.printStackTrace()
                }

            })
        }
    }

    private fun setMap(data: Spot) {
        mapView.removeAllPOIItems()
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(data.lcinfoLa, data.lcinfoLo), true);

        marker = MapPOIItem()
        marker.apply {
            itemName = data.bsshNm
            tag = 1
            mapPoint = MapPoint.mapPointWithGeoCoord(data.lcinfoLa, data.lcinfoLo)
            isCustomImageAutoscale = true // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
            setCustomImageAnchor(0.5f, 1.0f)
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        }
        mapView.addPOIItem(marker)
    }

    private fun setBitmap(icBaselineCall24: Int): Bitmap {
        return (ResourcesCompat.getDrawable(this.resources, icBaselineCall24, null) as VectorDrawable).toBitmap()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    inner class PanelEventListener : SlidingUpPanelLayout.PanelSlideListener {
        var text : String = "아래를 눌러 리스트 확인"
        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            binding.recyclerText.text = text
        }
        // 패널이 슬라이드 중일 때

        // 패널의 상태가 변했을 때
        override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?, newState: SlidingUpPanelLayout.PanelState?) {
            if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                text = "아래를 눌러 리스트 확인"
                binding.recyclerText.text = text
                binding.slidLayout.isTouchEnabled = true
            }
            else if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                text = "리스트 중 하나를 터치해 자세한 정보확인"
                binding.recyclerText.text = text
                binding.slidLayout.isTouchEnabled = false
                binding.callBtn.hide()
            }
        }
    }
}