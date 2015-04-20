/* SPARQL End points*/

/*1207*/	http://localhost:3030/myDataset/data/Surgerytypes
/*1208*/	http://localhost:3030/myDataset/data/PatientsType
/*1209*/	http://localhost:3030/myDataset/data/PatientsTypebyRace
/*1210*/	http://localhost:3030/myDataset/data/infrastructure
/*1211*/	http://localhost:3030/myDataset/data/accreditionstatus


/* Surgery Types & counts */
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT ?state ?city ?fac SUM(?count) as SUM
WHERE {
GRAPH <http://localhost:3030/myDataset/data/Surgerytypes> 
{
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :general_surgery ?count
}
}
group by ?state
limit 100

/* Patient Types & counts */
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT * 
WHERE {
GRAPH <http://localhost:3030/myDataset/data/PatientsType> 
{
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?obj.
  ?sub :male_outpatient ?male_outpatient.
  ?sub :female_outpatient ?female_outpatient
}
}
order by ?state
limit 100

/* Patient Types by Race*/

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT distinct ?state ?city ?fac ?afr_amr_patient_count
WHERE {
GRAPH <http://localhost:3030/myDataset/data/PatientsTypebyRace> 
{
  ?sub1 :state ?state.
  ?sub1 :city ?city.
  ?sub1 :facility_name ?fac.
  ?sub1 :african_american_outpatient ?afr_amr_patient_count
}
}
order by ?state
limit 100


/* Facilities & infrastructure */

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT distinct ?state ?city ?fac ?emergency_room_beds
WHERE {
GRAPH <http://localhost:3030/myDataset/data/infrastructure> 
{
  ?sub1 :state ?state.
  ?sub1 :city ?city.
  ?sub1 :facility_name ?fac.
  ?sub1 :emergency_room_beds ?emergency_room_beds
}
}
order by ?state
limit 100

/*Accredition status */

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT distinct ?state ?city ?fac ?carf_acc ?join_acc
WHERE {
GRAPH <http://localhost:3030/myDataset/data/accreditionstatus> 
{
  ?sub2 :state ?state.
  ?sub2 :city ?city.
  ?sub2 :facility_name ?fac.
  ?sub2 :carf_accreditation ?carf_acc.
  ?sub2 :joint_commission_accreditation ?join_acc
}
}
order by ?state
limit 100

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT distinct ?state ?city ?fac ?carf_acc ?join_acc
WHERE {
GRAPH <http://localhost:3030/myDataset/data/accreditionstatus> 
{
  ?sub2 :state ?state.
  ?sub2 :city ?city.
  ?sub2 :facility_name ?fac.
  ?sub2 :carf_accreditation ?carf_acc.
  ?sub2 :joint_commission_accreditation ?join_acc
}
}
order by ?state
limit 100

/*Surgery Counts*/

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?gen_count)) as ?count_gen_surgery) (sum(xsd:int(?card_count)) as ?count_card_surgery) (sum(xsd:int(?ortho_count)) as ?count_ortho_surgery) (sum(xsd:int(?vas_count)) as ?count_vascular_surgery) (sum(xsd:int(?other_count)) as ?count_other_surgery)
WHERE {
GRAPH <http://localhost:3030/myDataset/data/Surgerytypes> 
{
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :general_surgery ?gen_count.
  ?sub :cardiac ?card_count.
  ?sub :orthopedic ?ortho_count.
  ?sub :vascular_surgery ?vas_count.
  ?sub :other_surgery ?other_count.
}
}
group by ?state
order by ?state


/*Infrastructure Counts*/

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT distinct ?state ?city ?fac ?emergency_room_beds ?speciality_services ?intensive_care_unit ?intensive_care_unit_class ?maternity_care_available ?carf_accreditation ?joint_commission_accreditation
WHERE {
GRAPH <http://localhost:3030/myDataset/data/infrastructure> 
{
  ?sub1 :state ?state.
  ?sub1 :city ?city.
  ?sub1 :facility_name ?fac.
  ?sub1 :emergency_room_beds ?emergency_room_beds.
  ?sub1 :speciality_services ?speciality_services.
  ?sub1 :intensive_care_unit ?intensive_care_unit.
  ?sub1 :intensive_care_unit_class ?intensive_care_unit_class.
  ?sub1 :maternity_care_available ?maternity_care_available
}

GRAPH <http://localhost:3030/myDataset/data/accreditionstatus> 
{
  ?sub2 :state ?state.
  ?sub2 :city ?city.
  ?sub2 :facility_name ?fac.
  ?sub2 :carf_accreditation ?carf_accreditation.
  ?sub2 :joint_commission_accreditation ?joint_commission_accreditation
}
}
order by ?state


/* Infra Count */
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?emergency_room_beds)) as ?count_emergency_room_beds) 
WHERE {
GRAPH <http://localhost:3030/myDataset/data/infrastructure> 
{
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :emergency_room_beds ?emergency_room_beds.
}
}
group by ?state
order by ?state



/* Male/Female Patient count per State*/
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?mpatient)) as ?count_mpatient) (sum(xsd:int(?fpatient)) as ?count_fpatient)
WHERE {
GRAPH <http://localhost:3030/myDataset/data/PatientsType> 
{
  ?sub :state ?state.
  ?sub :female_outpatient ?fpatient.
  ?sub :male_outpatient ?mpatient
}
}
group by ?state
order by ?state



PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?afr_amr_patient_count)) as ?count_afr_amr_patient) (sum(xsd:int(?white_outpatient)) as ?count_white_patient) (sum(xsd:int(?other_outpatient)) as ?count_other_patient)
WHERE {
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :african_american_outpatient ?afr_amr_patient_count.
  ?sub :white_outpatient ?white_outpatient.
  ?sub :other_outpatient ?other_outpatient.    
}
group by ?state
order by ?state



