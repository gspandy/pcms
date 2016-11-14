package com.pujjr.base.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pujjr.base.dao.HolidayMapper;
import com.pujjr.base.domain.Holiday;
import com.pujjr.base.service.IHolidayService;
@Service
public class HolidayServiceImpl implements IHolidayService {

	@Autowired
	private HolidayMapper holidayDao;

	@Override
	public List<Holiday> getHolidayList() {
		// TODO Auto-generated method stub
		return holidayDao.selectAll();
	}

	@Override
	public Holiday getHolidayByDate(String id) {
		// TODO Auto-generated method stub
		return holidayDao.selectByPrimaryKey(id);
	}

	@Override
	public void addHoliday(Holiday record) throws Exception {
		// TODO Auto-generated method stub
		if(holidayDao.seleteByHolidayDate(record.getHolidayDate())!=null)
		{
			throw new Exception("已设置当前日期为节假日");
		}
		holidayDao.insert(record);
	}

	@Override
	public void modifyHoliday(Holiday record) {
		// TODO Auto-generated method stub
		holidayDao.updateByPrimaryKey(record);
	}

	@Override
	public void deleteHolidayById(String id) {
		// TODO Auto-generated method stub
		holidayDao.deleteByPrimaryKey(id);
	}

}
