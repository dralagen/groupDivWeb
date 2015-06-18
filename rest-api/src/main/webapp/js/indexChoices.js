var app = angular.module("groupDiv.choicesController", ['LocalStorageModule','pascalprecht.translate']);


app.controller("choicesController", ['$scope','$translate','localStorageService', function($scope,$translate,localStorageService){
	$scope.lang = 'Français (fr)';
	$scope.choice = true;
	if(localStorageService.get('language') == null)
		{$translate.use('fr'); }
	else
		{$translate.use(localStorageService.get('language'));}
	
	
	
	$scope.loadUserInterface = function(){
		$scope.choice = false;
	}
	
	$scope.loadAdminInterface = function(){
		$scope.choice = false;
	}
	
	$scope.submitLanguage = function(lang){
		return localStorageService.set('language', lang);
	};
	
	$scope.changeLanguageFr = function(){
		$translate.use('fr');
		$scope.lang = 'Français (fr)';
		$scope.submitLanguage('fr');
		console.log(localStorageService.get('language'));
		
	}	
	
	$scope.changeLanguageEn = function($translateProvider){
		$translate.use('en');
		$scope.lang = 'English (en)';
		$scope.submitLanguage('en');
		console.log(localStorageService.get('language'));
	};	
	

}]);

