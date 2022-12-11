package com.project.Main.Models.RevenueChart.Repositories;

import com.project.Main.Models.BookTypeChart.Factories.ChartFactory;
import com.project.Main.Models.BookTypeChart.IBookTypeChart;
import com.project.Main.Models.RevenueChart.IRevenueChart;
import com.project.Main.Database.DBConnection;
import com.project.Main.Database.IDBConnection;
import com.project.Main.Database.State.DatabaseState;
import com.project.Main.Database.State.SQLFailureState;
import com.project.Main.Database.State.SuccessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartRepository implements IChartRepository {

    public final Logger LOGGER = LogManager.getLogger(this.getClass());
    private final IDBConnection dbConn;

    public ChartRepository() {
        this.dbConn = DBConnection.instance();
    }

    public static List<Map.Entry<String, Integer>> loadFromResultSet(ResultSet resultSet) throws SQLException {
        List<Map.Entry<String, Integer>> chartData = new ArrayList<Map.Entry<String, Integer>>();

        while (resultSet.next()) {
            String type = resultSet.getString("type");
            int count = resultSet.getInt("count");

            Map.Entry<String, Integer> pair = new AbstractMap.SimpleEntry<>(type, count);
            chartData.add(pair);
        }

        return chartData;
    }

    public static List<Map.Entry<String, Double>> loadFromResultSetForRevenueChart(ResultSet resultSet) throws SQLException {

        List<Map.Entry<String, Double>> revenueChartData = new ArrayList<Map.Entry<String, Double>>();

        while (resultSet.next()) {
            String month = resultSet.getString("month");
            double monthly_payment = resultSet.getDouble("monthly_payment");

            Map.Entry<String, Double> pair = new AbstractMap.SimpleEntry<>(month, monthly_payment);
            revenueChartData.add(pair);
        }

        return revenueChartData;
    }

    @Override
    public Map.Entry<IBookTypeChart, DatabaseState> getTypeOfBookIssuedData() {

        try {
            Connection conn = this.dbConn.getConnection();
            String query = "select type, sum(total_quantity) as count from book group by type;";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map.Entry<String, Integer>> chartData = loadFromResultSet(resultSet);

            IBookTypeChart bookTypeChart = ChartFactory.instance().createBookTypeChart(chartData, this);

            return new AbstractMap.SimpleEntry<IBookTypeChart, DatabaseState>(bookTypeChart, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<IBookTypeChart, DatabaseState>(null, new SQLFailureState());
        }

    }

    @Override
    public Map.Entry<IRevenueChart, DatabaseState> getRevenueData() {

        try {
            Connection conn = this.dbConn.getConnection();
            String query = "SELECT MonthName(date) AS month, SUM(payment_value) as monthly_payment FROM payments where date >  now() - interval 12 month group by monthname(date);";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Map.Entry<String, Double>> revenueChartData = loadFromResultSetForRevenueChart(resultSet);
            IRevenueChart revenueChart = ChartFactory.instance().createRevenueChart(revenueChartData, this);

            return new AbstractMap.SimpleEntry<IRevenueChart, DatabaseState>(revenueChart, new SuccessState());
        } catch (SQLException e) {
            LOGGER.error("Database exception " + e.toString());

            return new AbstractMap.SimpleEntry<IRevenueChart, DatabaseState>(null, new SQLFailureState());
        }

    }
}
