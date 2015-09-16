package org.defence.tests.treeview_demo;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by root on 9/15/15.
 */
public class TreeViewDemo extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		List<City> cities = generateCitiesWithClients();

		VBox root = new VBox();
		Scene scene = new Scene(root);

		TreeView<Object> treeView = new TreeView<>();

		TreeItem<Object> rootItem = new TreeItem<>("Root");

		for (City city : cities) {
			rootItem.getChildren().add(createNode(city));
		}
		treeView.setRoot(rootItem);

		treeView.setCellFactory(param -> new TreeCellFactory());

		treeView.setMinWidth(400);

		root.getChildren().add(treeView);

		stage.setScene(scene);
		stage.setTitle("TreeView Demo");
		stage.setMinHeight(600);
		stage.setMinWidth(400);
		stage.show();
	}

	private TreeItem<Object> createNode(Object obj) {
		return new TreeItem<Object>(obj) {
			private boolean isLeaf;
			private boolean isFirstTimeChildren = true;
			private boolean isFirstTimeLeaf = true;

			@Override public ObservableList<TreeItem<Object>> getChildren() {
				if (isFirstTimeChildren) {
					isFirstTimeChildren = false;

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

			private ObservableList<TreeItem<Object>> buildChildren(TreeItem<Object> treeItem) {

				if (treeItem.getValue() instanceof City) {
					City city = (City) treeItem.getValue();
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

				if (treeItem.getValue() instanceof Client) {
					Client client = (Client) treeItem.getValue();
					if (client != null) {
						Set<Car> cars = client.getCars();
						if (cars != null) {
							ObservableList<TreeItem<Object>> children = FXCollections.observableArrayList();

							for (Car car : cars) {
								children.add(createNode(car));
							}
							return children;
						}
					}
				}

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

	private final class TreeCellFactory extends TreeCell<Object> {
		private ContextMenu clientMenu = new ContextMenu();
		private ContextMenu carMenu = new ContextMenu();

		public TreeCellFactory() {
			MenuItem clientMenuItem = new MenuItem("Client menu");
			clientMenu.getItems().add(clientMenuItem);

			MenuItem carMenuItem = new MenuItem("Car menu");
			carMenu.getItems().add(carMenuItem);
		}

		@Override
		public void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
			} else {
				setText(item.toString());
			}

			if (item instanceof Client) {
				System.out.println(item);
				setContextMenu(clientMenu);
			}

			if (item instanceof Car) {
				setContextMenu(carMenu);
			}
		}


	}
}
