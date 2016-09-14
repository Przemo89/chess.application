angular.module('app.component1').controller('ChallengeController', ['$scope', '$modal', '$http', 'ChallengeService', function($scope, $modal, $http, ChallengeService) {
   'use strict';

   var getSentChallenges;

  //  $scope.getSentChallenges = function() {
  //    $http.get('http://localhost:8090/chess/challenge/sentChallenges/2')
  //      .then(function(response) {
  //        $scope.data.sentChallenges = response.data;
  //        return $scope.data.sentChallenges;
  //      });
  //  };

   $scope.selectedRow = null;

   $scope.data = {
     form: {},
     isSentChallengesAvailable: false,
     sentChallenges: []
   };

   ChallengeService.getSentChallenges(50).then(function(response) {
     $scope.data.sentChallenges = response.data;
    }).then(function() {
      console.log($scope.data.sentChallenges);
      if ($scope.data.sentChallenges.length === 0) {
       $scope.data.isSentChallengesAvailable = false;
       return;
     };
     $scope.data.isSentChallengesAvailable = true;
   }, function errorCallback() {
    console.log('error');
  });

  //  console.log($scope.data.sentChallenges);

   $scope.setSelectedRow = function (index) {
     $scope.selectedRow = index;
   };

  //  $scope.edit = function () {
  //    $modal.open({
  //     templateUrl: "/component-1/modal-dialog/edit-book-modal-dialog.tpl.html",
  //     controller: "EditBookController",
  //     resolve: {
  //       idSelectedBook: function () {
  //         return $scope.data.books[$scope.selectedRow].id;
  //       }
  //     }
  //    });
  //  };
   //
  //  $scope.add = function () {
  //    $modal.open({
  //     templateUrl: "/component-1/modal-dialog/add-book-modal-dialog.tpl.html",
  //     controller: "AddBookController",
  //     resolve: {
  //       books: function () {
  //         return BookService.getBooks();
  //       }
  //     }
  //    });
  //  };

}]);
