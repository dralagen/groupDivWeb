	var app = angular.module("groupDiv.choicesController", []);
	app.controller("choicesController", ['$scope', function($scope){
		$scope.user = false;
		$scope.admin = false;
		$scope.choice = true;
		
		console.log("choicesController is load");
		
		$scope.loadUserInterface = function(){
			$scope.user = true;
			$scope.choice = false;
		}
		
		$scope.loadAdminInterface = function(){
			$scope.admin = true;
			$scope.choice = false;
		}
	}]);

