angular.module('myApp').controller('searchFlights', function($scope,$http, $filter) {
$scope.flights=[];
$scope.flight={};
$scope.connflight={};

    $scope.getflights = function(flight){

    $http({
        method: 'POST',
        url: 'http://localhost:9000/homepage',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
         transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        data: {
        deploc: flight.dep,
        arrloc: flight.arr,
        date: $filter('date')(flight.date, "dd/MM/yyyy"),
        sortchoice: flight.choice,
        connflightstatus: flight.connFlight
        },
    }).success(
                function(result){
                    $scope.flights=result;
                    $scope.flight={};
                    $scope.error = "";
    }).error(
                function(data,statusText,headers)
                    {
                        $scope.error = "No Records Found! Please Try Again! Error Status:"+statusText;
                    });
    };
});