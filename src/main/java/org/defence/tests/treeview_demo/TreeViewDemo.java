package org.defence.tests.treeview_demo;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 9/15/15.
 */
public class TreeViewDemo<T> extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		List<City> cities = generateCitiesWithClients();

		VBox root = new VBox();
		Scene scene = new Scene(root);

		TreeView<Object> treeView = new TreeView<>();
		/*TreeItem<City> rootItem = new TreeItem<>();

		TreeItem<Client> clientsItem = new TreeItem<>();

		for (City city : cities) {
			for (Client client : city.getClients()) {
				for (Car car : client.getCars()) {

				}
				clientsItem.getChildren().add(new TreeItem<>(client));
			}
//			rootItem.getChildren().add(new TreeItem<>(city));

			rootItem.getChildren().add(new TreeItem<>(city));
		}*/

		TreeItem<Object> rootItem = createNode(cities);
		treeView.setRoot(rootItem);

		treeView.setMinWidth(400);

		root.getChildren().add(treeView);

		stage.setScene(scene);
		stage.setTitle("TreeView Demo");
		stage.setMinHeight(600);
		stage.setMinWidth(400);
		stage.show();
	}

	private TreeItem<Object> createNode(Object obj) {
		return new TreeItem<Object>() {
			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			@Override public ObservableList<TreeItem<Object>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;

					// First getChildren() call, so we actually go off and
					// determine the children of the File contained in this TreeItem.
					super.getChildren().setAll(buildChildren(this));
				}
				return super.getChildren();
			}

			@Override public boolean isLeaf() {
				if (isFirstTimeLeaf) {
					isFirstTimeLeaf = false;
					if (obj instanceof Car) {
						isLeaf = true;
					}
				}

				return isLeaf;
			}

			private ObservableList<TreeItem<Object>> buildChildren(TreeItem<Object> TreeItem) {
				if (obj instanceof City) {
					City city = (City) TreeItem.getValue();
					if (city != null) {
						List<Client> clients = city.getClients();
						if (clients != null) {
							ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();

							for (Client client : clients) {
								children.add(createNode(client));
							}
							return children;
						}
					}
				}

				if (obj instanceof Client) {
					Client client = (Client) TreeItem.getValue();
					if (client != null) {
						Set<Car> cars = client.getCars();
						if (cars != null) {
							ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();

							for (Car car : cars) {
								children.add(createNode(client));
							}
							return children;
						}
					}
				}


				/*T f = TreeItem.getValue();
				if (f != null && f.isDirectory()) {
					File[] files = f.listFiles();
					if (files != null) {
						ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

						for (File childFile : files) {
							children.add(createNode(childFile));
						}

						return children;
					}
				}*/

				return FXCollections.emptyObservableList();
			}
		};
	}

	private List<City> generateCitiesWithClients() {
		Car skoda = new Car("Skoda", 2007);
		Car chevrolet = new Car("Chevrolet", 2014);
		Car vaz = new Car("Vaz 3110", 2000);

		Set<Car> lehasCars = new HashSet<>();
		lehasCars.add(skoda);

		Set<Car> kiryasCars = new HashSet<>();
		kiryasCars.add(chevrolet);

		Set<Car> denchiksCars = new HashSet<>();
		denchiksCars.add(vaz);

		Client leha = new Client("Алексей", lehasCars);
		Client kirya = new Client("Кирилл", kiryasCars);
		Client denchik = new Client("Денис", denchiksCars);


		List<Client> clients = new LinkedList<>();
		clients.add(leha);
		clients.add(kirya);
		clients.add(denchik);

		City kursk = new City("Курск");
		kursk.setClients(new ObservableListWrapper<Client>(clients));

		List<City> cities = new LinkedList<>();
		cities.add(kursk);

		return cities;
	}
}
