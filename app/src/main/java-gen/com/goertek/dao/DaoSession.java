package com.goertek.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.goertek.bean.CityAir;

import com.goertek.dao.CityAirDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cityAirDaoConfig;

    private final CityAirDao cityAirDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cityAirDaoConfig = daoConfigMap.get(CityAirDao.class).clone();
        cityAirDaoConfig.initIdentityScope(type);

        cityAirDao = new CityAirDao(cityAirDaoConfig, this);

        registerDao(CityAir.class, cityAirDao);
    }
    
    public void clear() {
        cityAirDaoConfig.getIdentityScope().clear();
    }

    public CityAirDao getCityAirDao() {
        return cityAirDao;
    }

}
