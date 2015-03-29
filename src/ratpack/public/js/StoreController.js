myApp.controller('StoreListCtrl', ['$scope', '$http',  function($scope, $http) {
 		$scope.listStores = function() {
 			$http({method: 'GET', url: './stores'}).
  				success(function(data, status, headers, config) {
    		    	$scope.stores = data;                  //set view model
    		    	$scope.view = './html/list.html'; //set to list view
  				}).
  				error(function(data, status, headers, config) {
  					$scope.stores = data || "Request failed";
  					$scope.status = status;
  					$scope.view = './html/list.html';
  				});
  		}

		$scope.showStore = function(storeId) {
			$http({method: 'GET', url: './stores/'+storeId}).
				success(function(data, status, headers, config) {
					$scope.store = data;                  //set view model
					$scope.view = './html/details.html'; //set to list view
				}).
				error(function(data, status, headers, config) {
					$scope.store = data || "Request failed";
					$scope.status = status;
					$scope.view = './html/details.html';
				});
		}

  		$scope.view = './html/list.html'; //set default view
 		$scope.listStores();
 }]);