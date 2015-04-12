/**
 * Created by Subbu on 4/10/15.
 */
var usStates = [
    { name: 'ALASKA', abbreviation: 'AK'},
    { name: 'ALABAMA', abbreviation: 'AL'},
    { name: 'ARKANSAS', abbreviation: 'AR'},
    { name: 'ARIZONA', abbreviation: 'AZ'},
    { name: 'CALIFORNIA', abbreviation: 'CA'},
    { name: 'COLORADO', abbreviation: 'CO'},
    { name: 'CONNECTICUT', abbreviation: 'CT'},
    { name: 'DISTRICT OF COLUMBIA', abbreviation: 'DC'},
    { name: 'DELAWARE', abbreviation: 'DE'},
    { name: 'FLORIDA', abbreviation: 'FL'},
    { name: 'GEORGIA', abbreviation: 'GA'},
    { name: 'HAWAII', abbreviation: 'HI'},
    { name: 'IOWA', abbreviation: 'IA'},
    { name: 'IDAHO', abbreviation: 'ID'},
    { name: 'ILLINOIS', abbreviation: 'IL'},
    { name: 'INDIANA', abbreviation: 'IN'},
    { name: 'KANSAS', abbreviation: 'KS'},
    { name: 'KENTUCKY', abbreviation: 'KY'},
    { name: 'LOUISIANA', abbreviation: 'LA'},
    { name: 'MASSACHUSETTS', abbreviation: 'MA'},
    { name: 'MARYLAND', abbreviation: 'MD'},
    { name: 'MAINE', abbreviation: 'ME'},
    { name: 'MICHIGAN', abbreviation: 'MI'},
    { name: 'MINNESOTA', abbreviation: 'MN'},
    { name: 'MISSOURI', abbreviation: 'MO'},
    { name: 'MISSISSIPPI', abbreviation: 'MS'},
    { name: 'MONTANA', abbreviation: 'MT'},
    { name: 'NORTH CAROLINA', abbreviation: 'NC'},
    { name: 'NORTH DAKOTA', abbreviation: 'ND'},
    { name: 'NEBRASKA', abbreviation: 'NE'},
    { name: 'NEW HAMPSHIRE', abbreviation: 'NH'},
    { name: 'NEW JERSEY', abbreviation: 'NJ'},
    { name: 'NEW MEXICO', abbreviation: 'NM'},
    { name: 'NEVADA', abbreviation: 'NV'},
    { name: 'NEW YORK', abbreviation: 'NY'},
    { name: 'OHIO', abbreviation: 'OH'},
    { name: 'OKLAHOMA', abbreviation: 'OK'},
    { name: 'OREGON', abbreviation: 'OR'},
    { name: 'PENNSYLVANIA', abbreviation: 'PA'},
    { name: 'PUERTO RICO', abbreviation: 'PR'},
    { name: 'RHODE ISLAND', abbreviation: 'RI'},
    { name: 'SOUTH CAROLINA', abbreviation: 'SC'},
    { name: 'SOUTH DAKOTA', abbreviation: 'SD'},
    { name: 'TENNESSEE', abbreviation: 'TN'},
    { name: 'TEXAS', abbreviation: 'TX'},
    { name: 'UTAH', abbreviation: 'UT'},
    { name: 'VIRGINIA', abbreviation: 'VA'},
    { name: 'VERMONT', abbreviation: 'VT'},
    { name: 'WASHINGTON', abbreviation: 'WA'},
    { name: 'WISCONSIN', abbreviation: 'WI'},
    { name: 'WEST VIRGINIA', abbreviation: 'WV'},
    { name: 'WYOMING', abbreviation: 'WY' }
];

var state_code_arr = {};
usStates.forEach(function(s){state_code_arr[s.abbreviation]=s.name});

function stateLookup(code) {
    return usStates[code].abbreviation;
}


// load google visualization packages
google.load('visualization', '0', {'packages': ['geomap','corechart','geochart']});

// set callback function for drawing visualizations
//google.setOnLoadCallback(drawSurgeryMap);

function prepareData(fileName){
    console.log("prepareData() entry");
    var data = null;

    $.ajax({
        url: fileName,
        type: 'get',
        async: false,
        success: function(csvString) {
            data = $.csv.toArrays(csvString, {onParseValue: $.csv.hooks.castToScalar});;
        },
        error: function(){
            alert("Error in Ajax call");
        }
    });
    console.log(data);
    return data;
}

function setupNewData(arrayData, columnName, colNum){
    console.log("setupNewData() entry");
    var data = new google.visualization.arrayToDataTable(arrayData);
    var newData = new google.visualization.DataTable();
    newData.addColumn('string', 'regions', 'regions');
    newData.addColumn('string', columnName,'count');
    console.log(data);
    var rows = data.getNumberOfRows();
    for (var i = 0; i < rows; i++ )
    {
        var state = 'US-' + stateLookup(i);
        var value =  ""+  data.getValue(i, colNum);
        newData.addRow([state, value]);
    }
    console.log("setupNewData() exit");
    return newData;
}

function drawSurgeryMap(){
    console.log("drawSurgeryMap() entry");
    var data = prepareData("data/surgeryCountPerState.csv");
    console.log(data);
    var newData = setupNewData(data, "General Surgery Count", 1);

    var options = {};
    options['region'] = 'US';   // show US map
    options['dataMode'] = 'regions';
    options['width'] = '100%';
    options['height'] = 600;
    options['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];

    var divId = document.getElementById('chart');
    var chart = new google.visualization.GeoMap(divId);
    chart.draw(newData, options );
    console.log("drawSurgeryMap() exit");
}

//
//function drawMap() {
//    // Send the query.
//    var queryurl = "js/sparql1.js";
//    var query = new google.visualization.Query(queryurl);
//    query.send(handleQueryResponse);
//}
//
//function handleQueryResponse(response){
//    // Check for query response errors.
//    if (response.isError()) {
//        alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
//        return;
//    }
//
//    // read data
//    var data = response.getDataTable();
//
//    // create new data
//    var newdata = new google.visualization.DataTable();
//    // configure map options
//    var options_2006 = {};
//    options_2006['region'] = 'US';   // show US map
//    options_2006['dataMode'] = 'regions';
//    options_2006['width'] = '100%';
//    options_2006['height'] = 600;
//    options_2006['colors'] = [0xADEBAD, 0x5C85FF, 0xFF1919];
//
//
//    newdata.addColumn('string', 'State');
//    newdata.addColumn('string', 'Total Expenditure 2006');
//    var rows = data.getNumberOfRows();
//    for (var i = 0; i < rows; i++ )
//    {
//        var state = 'US-' + stateLookup(i);
//        var value =  ""+  data.getValue(i, 1);
//        newdata.addRow([state, value]);
//    }
//
//    var divId= document.getElementById('idMap1');
//    var geomap_2006= new google.visualization.GeoMap(divId);
//    geomap_2006.draw(newdata, options_2006 );
//    drawCompChart_2006();
//}
//
//function drawCompChart_2006(state) {    // Send the query.
//    var queryurl1 = "js/sparqlEton.js";
//    var query1 = new google.visualization.Query(queryurl1); // Send the query.
//    newdata1 = new google.visualization.DataTable();
//    newdata1.addColumn('string', 'Area');
//    newdata1.addColumn('number', '#Rooms/#Count');
//    newdata1.addColumn({type: 'string', role: 'style'});
//    query1.send(handleQueryResponseComp2006);
//}
//
//function  handleQueryResponseComp2006(response){
//
//    // Check for query response errors
//    if (response.isError()) {
//        alert('Error in query: ' + response.getMessage() + ' ' + response.getDetailedMessage());
//        return;
//    }
//    // read data
//    var data1 = response.getDataTable();
//    var rows = data1.getNumberOfRows();
//    // create new data
//    newdata1.addRows(25);
//    for (var i = 0; i < 25; i++ )
//    {
//        newdata1.setValue(i,0,data1.getValue(i, 0));
//        newdata1.setValue(i,1,data1.getValue(i, 1));
//        newdata1.setValue(i,2,'stroke-color: #b87333; stroke-width: 4; fill-color: gold; fill-opacity: 0.2')
//    }
//
//    // configure  options
//    var options = {
//        title: '#ER/#NC',
//        legend: {position: 'none'},
//        hAxis: {title: 'Location', titleTextStyle: {color: 'black'}}
//    };
//
//    var divId = document.getElementById('idChart1');
//    var chart = new google.visualization.ColumnChart(divId);
//    chart.draw(newdata1, options );
//
//}
