var app = angular.module("groupDiv.choicesController", []);

app.controller("choicesController", ['$scope','$translate', function($scope,$translate){
	$scope.lang = 'Français (fr)';
	$scope.choice = true;
	
	$scope.loadUserInterface = function(){
		$scope.choice = false;
	}
	
	$scope.loadAdminInterface = function(){
		$scope.choice = false;
	}
	
	$scope.changeLanguageFr = function(){
		$translate.use('fr');
		$scope.lang = 'Français (fr)';
	}	
	
	$scope.changeLanguageEn = function($translateProvider){
		$translate.use('en');
		$scope.lang = 'English (en)';
	};	
}]);

