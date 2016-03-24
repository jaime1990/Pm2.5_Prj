package com.goertek.pmdemo.utils;

import android.content.Context;
import android.util.Log;

import com.goertek.bean.CityAir;
import com.goertek.dao.CityAirDao;
import com.goertek.dao.DaoSession;
import com.goertek.pmdemo.application.BaseApplication;

import java.util.List;

/**
 * Description:
 *
 * @author : jaime
 * @email : appeal1990@hotmail.com
 * @since : 16-3-23
 */
public class DbUtil {

    private static final String TAG = DbUtil.class.getSimpleName();
    private static DbUtil instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private CityAirDao airDao;

    public DbUtil(){}

    public static DbUtil getInstance(Context context){
        if(instance == null){
            instance = new DbUtil();
            if(appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = BaseApplication.getDaoSession(context);
            instance.airDao = instance.mDaoSession.getCityAirDao();
        }
        return instance;
    }

    public CityAir loadAir(long id){
        return airDao.load(id);
    }

    public List<CityAir> loadAll(){
        return airDao.loadAll();
    }

    public List<CityAir> queryNote(String where, String... params){
        return airDao.queryRaw(where, params);
    }

    public long saveAir(CityAir air){
        return airDao.insertOrReplace(air);
    }

    public void saveNoteLists(final List<CityAir> list){
        if(list == null || list.isEmpty()){
            return;
        }
        airDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    CityAir cityAir = list.get(i);
                    airDao.insertOrReplace(cityAir);
                }
            }
        });
    }

    public void deleteAllNote(){
        airDao.deleteAll();
    }

    /**
     * delete note by id
     * @param id
     */
    public void deleteNote(long id){
        airDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteNote(CityAir cityAir){
        airDao.delete(cityAir);
    }
}
