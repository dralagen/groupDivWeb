var app = angular.module('groupDiv.modifySession', []);

app.controller('modifySession', ['$scope','GApi' , function($scope,GApi){
	$scope.sessionChoosen = false;

	$scope.old = {};
	$scope.old.useGroupDiv = true;
	$scope.old.sessionName = "";

	$scope.new_ = {};
	$scope.new_.useGroupDiv = true;
	$scope.new_.sessionName = "";
	
	$scope.sessions = [];
	$scope.users = {};
	
	$scope.ues = [];
	$scope.uesToDelete = [];

	
	GApi.execute('groupDivWeb', 'session.list').then(
		function(data){
			console.log("list sessions ok ");
			angular.forEach(data.items, function(item){
				temp = {name: item.name, id: item.id};
				$scope.sessions.push(temp);
			});
		},
		function(){
			console.log("error : we can't get the list of sessions");
		}
	);
	
	$scope.deleteUe = function (ue) {
		var index = $scope.ues.indexOf(ue);
		$scope.ues.splice(index, 1);
		console.log(ue.id);
		if(ue.id != ""){
			$scope.uesToDelete.push(ue);
		}
	};
	
	$scope.addUe = function () {	
		var ue = {
			user: '',
			ue: '',
		}	
		$scope.ues.push(ue);
	};
	
	$scope.modifySession = function(){
		var reload = false;
		//ues to add
		var uesToAdd = [];
		angular.forEach($scope.ues, function(ue){
			if(ue.id == ""){
				uesToAdd.push(ue);
				reload = true;
			}
		});

		//ue to edit
		angular.forEach($scope.ues, function(ue){

			console.log({sessionId: $scope.selectedSession, ueId: ue.id, title: ue.title, user: $scope.users[ue.authorId].name});
			if(ue.id != "" && ue.change == true){
				GApi.execute('groupDivWeb', 'session.ue.edit', {sessionId: $scope.selectedSession, ueId: ue.id, title: ue.title, user: $scope.users[ue.authorId].name}).then(
					function(data){
						console.log("ue : " + ue.id + " is modify");
					},
					function(){
						console.log("ue : " + ue.id + " is not modify");
					}
				);
			}
		});
		
		//ues to delete
		angular.forEach($scope.uesToDelete, function(ue){
			GApi.execute('groupDivWeb', 'session.ue.delete', {sessionId: $scope.selectedSession, ueId: ue.id}).then(
				function(data){
					console.log("ue : " + ue.id + " deleted");
				},
				function(){
					console.log("ue : " + ue.id + " not deleted");
				}
			);
		});
		$scope.uesToDelete = [];
		
		//modify session name and withGD
		if(	$scope.old.useGroupDiv != $scope.new_.useGroupDiv || $scope.old.sessionName != $scope.new_.sessionName){
			GApi.execute('groupDivWeb', 'session.edit', {sessionId: $scope.selectedSession, name: $scope.new_.sessionName, withGroupDiv: $scope.new_.useGroupDiv}).then(
				function(data){
					console.log("modify session name and withGD ok ");
				},
				function(){
					console.log("error : we can't modify session name and withGD");
				}
			);
		}

		if(reload == true){
			$scope.chooseSession();
		}
	}

	
	$scope.chooseSession = function(){
		if(!angular.isUndefined($scope.selectedSession)){
			$scope.sessionChoosen = true;
			
			GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.selectedSession}).then(
				function(data){
					console.log("session loaded");
					$scope.old.useGroupDiv = data.withGroupDiv;
					$scope.old.sessionName = data.name;

					$scope.new_.useGroupDiv = data.withGroupDiv;
					$scope.new_.sessionName = data.name;

					$scope.useGroupDiv = data.withGroupDiv;

					angular.forEach(data.user, function(usr){
						$scope.users[usr.id] = usr;
					});

					$scope.ues = data.ue;
					angular.forEach($scope.ues, function(ue){
						ue.change = false;
					});
					
					$scope.sessionName = data.name;
				}, function(){
					console.log("error : we can't load the session");
				}
			);
			$scope.sortUeUserList();
		}
	};
	
	//get a list of the users that are assign to the ues
	$scope.sortUeUserList = function(){
		angular.forEach($scope.ues, function(ue){
			angular.forEach($scope.users, function(user){
				if(ue.authorId == user.id)
				{ 
					temp = {user: user.name, ue: ue.title, ueId: ue.id};
					$scope.ues.push(temp);
				}
			});
		});
	};

	$scope.updateUE = function(oneUE){
		oneUE.change = true;
	}
}]);
