myApp.controller('BookListCtrl', ['$scope', '$http',  function($scope, $http) {
 		$scope.listBooks = function() {
 			$http({method: 'GET', url: './talk'}).
  				success(function(data, status, headers, config) {
    		    	$scope.books = data;                  //set view model
    		    	$scope.view = './html/list.html'; //set to list view
  				}).
  				error(function(data, status, headers, config) {
  					$scope.books = data || "Request failed";
  					$scope.status = status;
  					$scope.view = './html/list.html';
  				});
  		}

  		$scope.view = './html/list.html'; //set default view
  		$scope.listBooks();
 	}]);