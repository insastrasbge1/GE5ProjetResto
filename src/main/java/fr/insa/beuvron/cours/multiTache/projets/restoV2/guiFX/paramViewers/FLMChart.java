/*
Copyright 2000- Francois de Bertrand de Beuvron

This file is part of CoursBeuvron.

CoursBeuvron is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

CoursBeuvron is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with CoursBeuvron.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.insa.beuvron.cours.multiTache.projets.restoV2.guiFX.paramViewers;

import fr.insa.beuvron.cours.melOptimisation.utils.FonctionLineaireParMorceaux;
import fr.insa.beuvron.cours.melOptimisation.utils.PointFLM;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;

/**
 *
 * @author francois
 */
public class FLMChart extends ScrollPane {

    private FonctionLineaireParMorceaux flm;
    private String title;
    private String xAxisTitle;
    private String yAxisTitle;
    
    private LineChart<Number, Number> chart;

    public FLMChart(FonctionLineaireParMorceaux flm,
            String title,String xAxisTitle,String yAxisTitle) {
        this.flm = flm;
        this.title = title;
        this.xAxisTitle = xAxisTitle;
        this.yAxisTitle = yAxisTitle;
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(this.xAxisTitle);
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(this.yAxisTitle);
        this.chart = new LineChart<>(xAxis, yAxis);
        this.chart.setTitle(title);
        this.updateView();
        this.setContent(this.chart);
    }

    public final void updateView() {
        this.chart.getData().clear();
        XYChart.Series series = new XYChart.Series();
        series.setName(this.title);
        //populating the series with data
        for (PointFLM p : this.flm.getPoints()) {
            series.getData().add(new XYChart.Data(p.getX(), p.getY()));
        }
        this.chart.getData().add(series);
    }

}
