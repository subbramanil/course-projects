/**
 * Created by Subbu on 4/10/15.
 */
var newdata1;
var newdata2;

function stateLookup(code) {
    var state_code_arr = new Array();
    state_code_arr['0'] = 'AL';
    state_code_arr['1'] = 'AL';
    state_code_arr['2'] = 'AZ';
    state_code_arr['3'] = 'AR';
    state_code_arr['4'] = 'CA';
    state_code_arr['5'] = 'CO';
    state_code_arr['6'] = 'CT';
    state_code_arr['7'] = 'DW';
    state_code_arr['8'] = 'DC';
    state_code_arr['9'] = 'FL';
    state_code_arr['10'] = 'GA';
    state_code_arr['11'] = 'HI';
    state_code_arr['12'] = 'ID';
    state_code_arr['13'] = 'IL';
    state_code_arr['14'] = 'IN';
    state_code_arr['15'] = 'IA';
    state_code_arr['16'] = 'KS';
    state_code_arr['17'] = 'KY';
    state_code_arr['18'] = 'LA';
    state_code_arr['19'] = 'ME';
    state_code_arr['20'] = 'MD';
    state_code_arr['21'] = 'MA';
    state_code_arr['22'] = 'MI';
    state_code_arr['23'] = 'MN';
    state_code_arr['24'] = 'MS';
    state_code_arr['25'] = 'MO';
    state_code_arr['26'] = 'MT';
    state_code_arr['27'] = 'NE';
    state_code_arr['28'] = 'NV';
    state_code_arr['29'] = 'NH';
    state_code_arr['30'] = 'NJ';
    state_code_arr['31'] = 'NM';
    state_code_arr['32'] = 'NY';
    state_code_arr['33'] = 'NC';
    state_code_arr['34'] = 'ND';
    state_code_arr['35'] = 'OH';
    state_code_arr['36'] = 'OK';
    state_code_arr['37'] = 'OR';
    state_code_arr['38'] = 'PA';
    state_code_arr['39'] = 'RI';
    state_code_arr['40'] = 'SD';
    state_code_arr['41'] = 'TN';
    state_code_arr['42'] = 'TX';
    state_code_arr['43'] = 'UT';
    state_code_arr['44'] = 'VT';
    state_code_arr['45'] = 'VA';
    state_code_arr['46'] = 'WA';
    state_code_arr['47'] = 'WV';
    state_code_arr['48'] = 'WI';
    state_code_arr['49'] = 'WY';
    state_code_arr['50'] = 'SC';
    return state_code_arr[code];
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
