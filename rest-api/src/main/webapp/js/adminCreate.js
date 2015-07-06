(function(){
	var app = angular.module('groupDiv.createUeUser', []);


	app.controller('createUeUser', ['$scope', 'GApi','$translate', function($scope, GApi,$translate){

		$scope.session = {};
		$scope.session.ue = [];
		$scope.alerts = [];

		$scope.deleteUe = function (ue) {
			var index = $scope.session.ue.indexOf(ue);
			$scope.session.ue.splice(index, 1);
		};

		$scope.addUe = function () {
			var ue = {
				title: '',
				user: '',
			}
			$scope.session.ue.push(ue);
		};

		$scope.createSession = function () {
			console.log($scope.session);
			GApi.execute('groupDivWeb', 'session.post', $scope.session).then(
				function(resp) {
					console.log("session creted");
					$scope.alerts.push({type: 'success', msg: "Congratulation, session created!"});
					$scope.session = {};
					$scope.session.ue = [];
				}, function(err) {
					$scope.alerts =[];
					console.log("error you can't create that session : ");
					console.log(angular.fromJson(err.error.message));
					$scope.error = angular.fromJson(err.error.message);
					angular.forEach($scope.error, function(value, key) {
						$scope.alerts.push({type: 'warning', msg: $translate.instant(value)});
					});
				}
			);
		};

		$scope.closeAlert = function(index) {
			$scope.alerts.splice(index, 1);
		};

	}]);
})();
