package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Initializable {

	//creating fxml links to textfields
	@FXML public  TextField nameProd;
	@FXML public TextField priceProd;
	@FXML public TextField quanProd;
	@FXML public TextField infoProd;
	@FXML public ListView listProd;
	@FXML public Label labelInfo;
	@FXML public Label plValLabel;
	@FXML public Label invenValLabel;
	double buy;
	double sell;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void searchProdB(ActionEvent actionEvent) {
		String forsearch= infoProd.getText();
		ObservableList<String> listOfItems = listProd.getItems();
		for (String each: listOfItems) {
			if(each.startsWith(forsearch)) {
				String price = each.substring(each.indexOf("p:")+2, each.indexOf("|  q:"));
				String quantity = each.substring(each.indexOf("q:")+2, each.indexOf("| Tot:"));
				String tot = each.substring(each.indexOf("Tot:")+4, each.length());
				String productInfo = forsearch + ": bought at a price of ₹" + price +"\n"+"The quantity is: " + quantity + "\n" + "Total cost of purchase is ₹" + tot;
				labelInfo.setText(productInfo);
			}
		}


	}

	public void shipProdB(ActionEvent actionEvent) {
		String selected= (String) listProd.getSelectionModel().getSelectedItem();
		if(selected!=null){
			listProd.getItems().remove(selected);
			double totextract=Double.parseDouble(selected.substring(selected.indexOf("Tot:")+4,selected.length()));
			int quantity = Integer.parseInt((selected.substring(selected.indexOf("q:")+2,selected.indexOf(" | Tot:"))));
			double sp = Double.parseDouble(priceProd.getText());
			sell+=(quantity*sp);
			System.out.println(sell);
		}else{
			System.out.println("No task selected..");
		}
		changeprofloss();
		totInvenCost();
	}

	public void addProdB(ActionEvent actionEvent) {
		String text = nameProd.getText();
		if(!text.equals("")){
			String a=nameProd.getText();
			String b=quanProd.getText();
			String c=priceProd.getText();
			String bc=String.valueOf(Integer.parseInt(b)*Integer.parseInt(c));
			String final1=a+" | "+" p:"+c+" | "+" q:"+b+" | Tot: "+bc;
			listProd.getItems().add(final1);
			nameProd.setText("");
			priceProd.setText("");
			quanProd.setText("");
			buy+=Double.parseDouble(bc);
		}else{
			System.out.println("No input..");
		}
		changeprofloss();
		totInvenCost();
	}

	public void changeprofloss(){
		double profloss = sell-buy;
		plValLabel.setText(String.valueOf(profloss));
	}

	public void totInvenCost(){
		invenValLabel.setText(String.valueOf(buy));
	}

	public void createNew(){
		nameProd.clear();
		quanProd.clear();
		priceProd.clear();
		infoProd.clear();
		listProd.getItems().clear();
		labelInfo.setText(null);
		plValLabel.setText(null);
		invenValLabel.setText(null);
	}
}