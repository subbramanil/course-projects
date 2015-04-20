/*Surgery Types*/
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?gen_count)) as ?count_gen_surgery) (sum(xsd:int(?card_count)) as ?count_card_surgery) (sum(xsd:int(?ortho_count)) as ?count_ortho_surgery) (sum(xsd:int(?vas_count)) as ?count_vascular_surgery) (sum(xsd:int(?other_count)) as ?count_other_surgery)
WHERE {
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :general_surgery ?gen_count.
  ?sub :cardiac ?card_count.
  ?sub :orthopedic ?ortho_count.
  ?sub :vascular_surgery ?vas_count.
  ?sub :other_surgery ?other_count.
}
group by ?state
order by ?state

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?gen_count)) as ?count_gen_surgery) (sum(xsd:int(?card_count)) as ?count_card_surgery) (sum(xsd:int(?ortho_count)) as ?count_ortho_surgery) (sum(xsd:int(?vas_count)) as ?count_vascular_surgery) (sum(xsd:int(?other_count)) as ?count_other_surgery)
WHERE {
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :general_surgery ?gen_count.
  ?sub :cardiac ?card_count.
  ?sub :orthopedic ?ortho_count.
  ?sub :vascular_surgery ?vas_count.
  ?sub :other_surgery ?other_count.
}
group by ?state
order by ?state


/*Emerg. beds*/

PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?emergency_room_beds)) as ?count_emergency_room_beds) 
WHERE {
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :emergency_room_beds ?emergency_room_beds.
}
group by ?state
order by ?state



PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
SELECT distinct ?state ?city ?fac ?emergency_room_beds ?speciality_services ?intensive_care_unit ?intensive_care_unit_class ?maternity_care_available ?carf_accreditation ?joint_commission_accreditation
WHERE {
  ?sub1 :state ?state.
  ?sub1 :city ?city.
  ?sub1 :facility_name ?fac.
  ?sub1 :emergency_room_beds ?emergency_room_beds.
  ?sub1 :speciality_services ?speciality_services.
  ?sub1 :intensive_care_unit ?intensive_care_unit.
  ?sub1 :intensive_care_unit_class ?intensive_care_unit_class.
  ?sub1 :maternity_care_available ?maternity_care_available.
  ?sub2 :carf_accreditation ?carf_accreditation.
  ?sub2 :joint_commission_accreditation ?joint_commission_accreditation
}
order by ?state


PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?afr_amr_patient_out_count)) + sum(xsd:int(?white_outpatient_count)) + sum(xsd:int(?other_outpatient_count)) as ?total_out_patient)
(sum(xsd:int(?afr_amr_patient_in_count)) + sum(xsd:int(?white_inpatient_count)) + sum(xsd:int(?other_inpatient_count)) as ?total_in_patient)
WHERE {
  ?sub :state ?state.
  ?sub :city ?city.
  ?sub :facility_name ?fac.
  ?sub :african_american_outpatient ?afr_amr_patient_out_count.
  ?sub :white_outpatient ?white_outpatient_count.
  ?sub :other_outpatient ?other_outpatient_count.
  ?sub :african_american_inpatient ?afr_amr_patient_in_count.
  ?sub :white_inpatient ?white_inpatient_count.
  ?sub :other_inpatient ?other_inpatient_count.
}
group by ?state
order by ?state



PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?_65yo_outpatient)) as ?_65yo_outpatient_count) (sum(xsd:int(?disabled_p1_p4_inpatient)) as ?disabled_p1_p4_inpatient_count) (sum(xsd:int(?disabled_p1_p4_outpatient)) as ?disabled_p1_p4_outpatient_count) (sum(xsd:int(?female_outpatient)) as ?female_outpatient_count) (sum(xsd:int(?geriatric_65_yo_inpatient)) as ?geriatric_65_yo_inpatient_count) (sum(xsd:int(?geriatric_65_yo_outpatient)) as ?geriatric_65_yo_outpatient_count) (sum(xsd:int(?geriatric_65yo_inpatient)) as ?geriatric_65yo_inpatient_count) (sum(xsd:int(?homeless_outpateint)) as ?homeless_outpateint_count) (sum(xsd:int(?male_outpatient)) as ?male_outpatient_count) (sum(xsd:int(?mental_health_outpatient)) as ?mental_health_outpatient_count) (sum(xsd:int(?non_mental_health_outpatient)) as ?non_mental_health_outpatient_count)
WHERE {
  ?sub :_65yo_outpatient ?_65yo_outpatient.
  ?sub :disabled_p1_p4_inpatient ?disabled_p1_p4_inpatient.
  ?sub :disabled_p1_p4_outpatient ?disabled_p1_p4_outpatient.
  ?sub :female_outpatient ?female_outpatient.
  ?sub :geriatric_65_yo_outpatient ?geriatric_65_yo_outpatient.
  ?sub :geriatric_65_yo_inpatient ?geriatric_65_yo_inpatient.
  ?sub :geriatric_65yo_inpatient ?geriatric_65yo_inpatient.
  ?sub :homeless_outpateint ?homeless_outpateint.
  ?sub :mental_health_outpatient ?mental_health_outpatient.
  ?sub :non_mental_health_outpatient ?non_mental_health_outpatient.
  ?sub :male_outpatient ?male_outpatient.
  ?sub :state ?state
}
group by ?state
order by ?state




/*Patients By Type*/
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://data-gov.tw.rpi.edu/vocab/p/1202/>
PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
SELECT ?state (sum(xsd:int(?afr_amr_outpatient_count)) as ?count_afr_amr_outpatient_count) (sum(xsd:int(?afr_amr_inpatient_count)) as ?count_afr_amr_inpatient_count) (sum(xsd:int(?other_inpatient)) as ?count_other_inpatient) (sum(xsd:int(?other_outpatient)) as ?count_other_outpatient) (sum(xsd:int(?white_inpatient)) as ?count_white_inpatient) (sum(xsd:int(?white_outpatient)) as ?count_white_outpatient)
WHERE {
  ?sub1 :state ?state.
  ?sub1 :african_american_outpatient ?afr_amr_outpatient_count.
  ?sub1 :african_american_inpatient ?afr_amr_inpatient_count.
  ?sub1 :other_inpatient ?other_inpatient.
  ?sub1 :other_outpatient ?other_outpatient.
  ?sub1 :white_inpatient ?white_inpatient.
  ?sub1 :white_outpatient ?white_outpatient
}
group by ?state
order by ?state