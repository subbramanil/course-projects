/**
 * Created by Subbu on 4/10/15.
 */
var usStates = [
    {name: 'ALASKA', abbreviation: 'AK'},
    {name: 'ALABAMA', abbreviation: 'AL'},
    {name: 'ARKANSAS', abbreviation: 'AR'},
    {name: 'ARIZONA', abbreviation: 'AZ'},
    {name: 'CALIFORNIA', abbreviation: 'CA'},
    {name: 'COLORADO', abbreviation: 'CO'},
    {name: 'CONNECTICUT', abbreviation: 'CT'},
    {name: 'DISTRICT OF COLUMBIA', abbreviation: 'DC'},
    {name: 'DELAWARE', abbreviation: 'DE'},
    {name: 'FLORIDA', abbreviation: 'FL'},
    {name: 'GEORGIA', abbreviation: 'GA'},
    {name: 'HAWAII', abbreviation: 'HI'},
    {name: 'IOWA', abbreviation: 'IA'},
    {name: 'IDAHO', abbreviation: 'ID'},
    {name: 'ILLINOIS', abbreviation: 'IL'},
    {name: 'INDIANA', abbreviation: 'IN'},
    {name: 'KANSAS', abbreviation: 'KS'},
    {name: 'KENTUCKY', abbreviation: 'KY'},
    {name: 'LOUISIANA', abbreviation: 'LA'},
    {name: 'MASSACHUSETTS', abbreviation: 'MA'},
    {name: 'MARYLAND', abbreviation: 'MD'},
    {name: 'MAINE', abbreviation: 'ME'},
    {name: 'MICHIGAN', abbreviation: 'MI'},
    {name: 'MINNESOTA', abbreviation: 'MN'},
    {name: 'MISSOURI', abbreviation: 'MO'},
    {name: 'MISSISSIPPI', abbreviation: 'MS'},
    {name: 'MONTANA', abbreviation: 'MT'},
    {name: 'NORTH CAROLINA', abbreviation: 'NC'},
    {name: 'NORTH DAKOTA', abbreviation: 'ND'},
    {name: 'NEBRASKA', abbreviation: 'NE'},
    {name: 'NEW HAMPSHIRE', abbreviation: 'NH'},
    {name: 'NEW JERSEY', abbreviation: 'NJ'},
    {name: 'NEW MEXICO', abbreviation: 'NM'},
    {name: 'NEVADA', abbreviation: 'NV'},
    {name: 'NEW YORK', abbreviation: 'NY'},
    {name: 'OHIO', abbreviation: 'OH'},
    {name: 'OKLAHOMA', abbreviation: 'OK'},
    {name: 'OREGON', abbreviation: 'OR'},
    {name: 'PENNSYLVANIA', abbreviation: 'PA'},
    {name: 'PUERTO RICO', abbreviation: 'PR'},
    {name: 'RHODE ISLAND', abbreviation: 'RI'},
    {name: 'SOUTH CAROLINA', abbreviation: 'SC'},
    {name: 'SOUTH DAKOTA', abbreviation: 'SD'},
    {name: 'TENNESSEE', abbreviation: 'TN'},
    {name: 'TEXAS', abbreviation: 'TX'},
    {name: 'UTAH', abbreviation: 'UT'},
    {name: 'VIRGINIA', abbreviation: 'VA'},
    {name: 'VERMONT', abbreviation: 'VT'},
    {name: 'WASHINGTON', abbreviation: 'WA'},
    {name: 'WISCONSIN', abbreviation: 'WI'},
    {name: 'WEST VIRGINIA', abbreviation: 'WV'},
    {name: 'WYOMING', abbreviation: 'WY'}
];

var state_code_arr = {};
usStates.forEach(function (s) {
    state_code_arr[s.abbreviation] = s.name
});

function stateLookup(code) {
    return usStates[code].abbreviation;
}

// load google visualization packages
google.load('visualization', '0', {'packages': ['geomap', 'corechart', 'geochart', 'bar']});

/** Script for Styles */
$('#tab1 a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')
});
$('#tab2 a').click(function (e) {
    e.preventDefault()
    $(this).tab('show')
});


function setupNewData(arrayData, columnName, colNum) {
    console.log("setupNewData() entry");
    var data = new google.visualization.arrayToDataTable(arrayData);
    var newData = new google.visualization.DataTable();
    newData.addColumn('string', 'regions', 'regions');
    newData.addColumn('string', columnName, 'count');
    console.log(data);
    var rows = data.getNumberOfRows();
    for (var i = 0; i < rows; i++) {
        var state = 'US-' + stateLookup(i);
        var value = "" + data.getValue(i, colNum);
        newData.addRow([state, value]);
    }
    console.log("setupNewData() exit");
    return newData;
}

var query_surgeryTypes = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    "PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/> " +
    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
    "SELECT ?state (sum(xsd:int(?gen_count)) as ?count_gen_surgery) (sum(xsd:int(?card_count)) as ?count_card_surgery) (sum(xsd:int(?ortho_count)) as ?count_ortho_surgery) (sum(xsd:int(?vas_count)) as ?count_vascular_surgery) (sum(xsd:int(?other_count)) as ?count_other_surgery)" +
    "WHERE { " +
    "?sub :state ?state. " +
    "?sub :city ?city. " +
    "?sub :facility_name ?fac. " +
    "?sub :general_surgery ?gen_count. " +
    "?sub :cardiac ?card_count. " +
    "?sub :orthopedic ?ortho_count. " +
    "?sub :vascular_surgery ?vas_count. " +
    "?sub :other_surgery ?other_count." +
    "}" +
    "group by ?state " +
    "order by ?state";

var query_surgeryTypesByState = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    "PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/> " +
    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
    "SELECT ?state ?city (sum(xsd:int(?gen_count)) as ?count_gen_surgery) (sum(xsd:int(?card_count)) as ?count_card_surgery) (sum(xsd:int(?ortho_count)) as ?count_ortho_surgery) (sum(xsd:int(?vas_count)) as ?count_vascular_surgery) (sum(xsd:int(?other_count)) as ?count_other_surgery)" +
    "WHERE { " +
    "?sub :state ?state. " +
    "?sub :city ?city. " +
    "?sub :facility_name ?fac. " +
    "?sub :general_surgery ?gen_count. " +
    "?sub :cardiac ?card_count. " +
    "?sub :orthopedic ?ortho_count. " +
    "?sub :vascular_surgery ?vas_count. " +
    "?sub :other_surgery ?other_count." +
    "}" +
    "group by ?state ?city " +
    "order by ?state";

var query_emergBeds = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    "PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/> " +
    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
    "SELECT ?state (sum(xsd:int(?emergency_room_beds)) as ?count_emergency_room_beds) " +
    "WHERE { " +
    "?sub :state ?state." +
    "?sub :city ?city. " +
    "?sub :facility_name ?fac. " +
    "?sub :emergency_room_beds ?emergency_room_beds." +
    "}" +
    "group by ?state " +
    "order by ?state";

var query_loadInfraData = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    "PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/> " +
    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
    "SELECT distinct ?state ?city ?fac ?emergency_room_beds ?speciality_services ?intensive_care_unit ?intensive_care_unit_class ?maternity_care_available ?carf_accreditation ?joint_commission_accreditation " +
    "WHERE { " +
    "?sub1 :state ?state. " +
    "?sub1 :city ?city. " +
    "?sub1 :facility_name ?fac. " +
    "?sub1 :emergency_room_beds ?emergency_room_beds. " +
    "?sub1 :speciality_services ?speciality_services. " +
    "?sub1 :intensive_care_unit ?intensive_care_unit. " +
    "?sub1 :intensive_care_unit_class ?intensive_care_unit_class. " +
    "?sub1 :maternity_care_available ?maternity_care_available. " +
    "?sub2 :carf_accreditation ?carf_accreditation. " +
    "?sub2 :joint_commission_accreditation ?joint_commission_accreditation" +
    "}" +
    "order by ?state";

var query_loadTotalPatientsData = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    "PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/> " +
    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
    "SELECT ?state (sum(xsd:int(?afr_amr_patient_out_count)) + sum(xsd:int(?white_outpatient_count)) + sum(xsd:int(?other_outpatient_count)) as ?total_out_patient) " +
    "(sum(xsd:int(?afr_amr_patient_in_count)) + sum(xsd:int(?white_inpatient_count)) + sum(xsd:int(?other_inpatient_count)) as ?total_in_patient)" +
    "WHERE { " +
    "?sub1 :state ?state. " +
    "?sub :african_american_outpatient ?afr_amr_patient_out_count. " +
    "?sub :white_outpatient ?white_outpatient_count. " +
    "?sub :other_outpatient ?other_outpatient_count. " +
    "?sub :african_american_inpatient ?afr_amr_patient_in_count. " +
    "?sub :white_inpatient ?white_inpatient_count. " +
    "?sub :other_inpatient ?other_inpatient_count. " +
    "}" +
    "group by ?state " +
    "order by ?state";

var query_loadPatientsByRace = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
    "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
    "PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/> " +
    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> " +
    "SELECT ?state (sum(xsd:int(?afr_amr_inpatient_count)) as ?count_afr_amr_inpatient_count) (sum(xsd:int(?afr_amr_outpatient_count)) as ?count_afr_amr_outpatient_count) (sum(xsd:int(?white_inpatient)) as ?count_white_inpatient) (sum(xsd:int(?white_outpatient)) as ?count_white_outpatient) (sum(xsd:int(?other_inpatient)) as ?count_other_inpatient) (sum(xsd:int(?other_outpatient)) as ?count_other_outpatient)  " +
    "WHERE { " +
    "?sub1 :state ?state. " +
    "?sub1 :african_american_inpatient ?afr_amr_inpatient_count. " +
    "?sub1 :african_american_outpatient ?afr_amr_outpatient_count. " +
    "?sub1 :white_inpatient ?white_inpatient. " +
    "?sub1 :white_outpatient ?white_outpatient." +
    "?sub1 :other_inpatient ?other_inpatient. " +
    "?sub1 :other_outpatient ?other_outpatient " +
    "} " +
    "group by ?state " +
    "order by ?state";