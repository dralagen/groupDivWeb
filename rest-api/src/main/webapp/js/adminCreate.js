(function(){
	var app = angular.module('groupDiv.createUeUser', []);


	app.controller('createUeUser', ['$scope', 'GApi', function($scope, GApi){
			
		$scope.session = {};
		$scope.session.ues = [];
		$scope.alerts = [];
		
		$scope.deleteUe = function (ue) {
			var index = $scope.session.ues.indexOf(ue);
			$scope.session.ues.splice(index, 1);
		};	
		
		$scope.addUe = function () {	
			var ue = {
				title: '',
				author: {
				 name: '',
				}
			}	
			$scope.session.ues.push(ue);
		};
		
		$scope.createSession = function () {

			GApi.execute('groupDivWeb', 'session.post', $scope.session).then(
				function(resp) {
					console.log("session creted");
					$scope.alerts.push({type: 'success', msg: "Congratulation, session created!"});
					$scope.session = {};
					$scope.session.ues = [];
				}, function() {
					console.log("error you can't create that session :(:(:(:(:(:( ");
					$scope.alerts.push({type: 'warning', msg: "Warning, the session can't be created, check if you don't make some mistakes!"});
				}
			);
		};
		
		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};
	
	}]);
})();
