package com.project.Main.Models.RevenueChart.Repositories;

import com.project.Main.Models.BookTypeChart.IBookTypeChart;
import com.project.Main.Models.RevenueChart.IRevenueChart;
import com.project.Main.Database.State.DatabaseState;

import java.util.Map;

public interface IChartRepository {

    public Map.Entry<IBookTypeChart, DatabaseState> getTypeOfBookIssuedData();

    public Map.Entry<IRevenueChart, DatabaseState> getRevenueData();

}
