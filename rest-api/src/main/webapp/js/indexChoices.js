var app = angular.module("groupDiv.choicesController", []);

app.controller("choicesController", ['$scope', function($scope){

	$scope.choice = true;
	
	$scope.loadUserInterface = function(){
		$scope.choice = false;
	}
	
	$scope.loadAdminInterface = function(){
		$scope.choice = false;
	}
}]);

