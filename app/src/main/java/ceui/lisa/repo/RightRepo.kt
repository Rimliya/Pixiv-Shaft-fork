package ceui.lisa.repo

import android.content.Context
import ceui.lisa.R
import ceui.lisa.core.FilterMapper
import ceui.lisa.core.RemoteRepo
import ceui.lisa.http.Retro
import ceui.lisa.model.ListIllust
import ceui.lisa.utils.Dev
import com.scwang.smartrefresh.header.DeliveryHeader
import com.scwang.smartrefresh.layout.api.RefreshFooter
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.reactivex.Observable
import io.reactivex.functions.Function

class RightRepo(var restrict: String?) : RemoteRepo<ListIllust>() {

    override fun initApi(): Observable<ListIllust> {
        return Retro.getAppApi().getFollowUserIllust(token(), restrict)
    }

    override fun initNextApi(): Observable<ListIllust> {
        return Retro.getAppApi().getNextIllust(token(), nextUrl)
    }

    override fun getFooter(context: Context): RefreshFooter {
        return ClassicsFooter(context).setPrimaryColor(context.resources.getColor(R.color.fragment_center))
    }

    override fun getHeader(context: Context): RefreshHeader {
        return ClassicsHeader(context).setPrimaryColor(context.resources.getColor(R.color.fragment_center))
    }

    override fun mapper(): Function<ListIllust, ListIllust> {
        return FilterMapper()
    }

    override fun localData(): Boolean {
        return Dev.isDev
    }
}