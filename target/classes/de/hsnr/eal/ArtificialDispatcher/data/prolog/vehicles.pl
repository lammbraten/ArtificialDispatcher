%atna = Atemschutznotfallausrüstung
vehicleEquipment('HLF-BF', ['tragbareLeiter', 'brandbekämpfung', 'rettungssatz', 'hebekissen', ['tank', 2000]] ).
vehicleEquipment('HLF', ['tragbareLeiter', 'brandbekämpfung', 'rettungssatz', ['tank', 2000]] ).
vehicleEquipment('HLF-A', ['tragbareLeiter', 'brandbekämpfung', 'rettungssatz', 'atna', ['tank', 2000]] ). 
vehicleEquipment('LF8', ['tragbareLeiter', 'brandbekämpfung', ['tank', 600]] ). 
vehicleEquipment('LF20', ['tragbareLeiter', 'brandbekämpfung', ['tank', 1600]] ). 
vehicleEquipment('ELW', ['funkplatz', 'laufkarten']). 
vehicleEquipment('RW2-K', ['tragbareLeiter', 'hebekissen', 'kran', 'rettungssatz']). 
vehicleEquipment('GTLF', ['brandbekämpfung', ['tank', 8000]] ). 
vehicleEquipment('DLK-23', ['drehleiter'] ). 

vehicle(000, 'ELW', 'ELW-1-1', 01).
vehicle(001, 'HLF-BF', 'HLF-1-1', 01).
vehicle(002, 'DLK-23', 'DLK-1-1', 01).
vehicle(003, 'HLF-BF', 'HLF-1-2', 01).
vehicle(004, 'RW2-K', 'RW-1-1', 01).
vehicle(005, 'GTLF', 'GTLF-1-1', 01).

vehicle(006, 'HLF-BF', 'HLF-2-1', 02).
vehicle(007, 'DLK-23', 'DLK-2-1', 02).

vehicle(008, 'HLF', 'HLF-3-1', 03).
vehicle(009, 'HLF-A', 'HLF-3-2', 03).
vehicle(010, 'HLF', 'HLF-3-3', 03).
vehicle(011, 'DLK-23', 'DLK-3-1', 03).

vehicle(012, 'LF8', 'LF-4-1', 04).

vehicle(013, 'HLF-A', 'HLF-5-1', 05).

vehicle(014, 'HLF-A', 'HLF-6-1', 06).
vehicle(015, 'LF20', 'LF-6-1', 06).

vehicle(016, 'HLF-A', 'HLF-7-1', 07).
vehicle(017, 'LF20', 'LF-7-1', 07).

vehicle(018, 'LF8', 'LF-8-1', 08).
vehicle(019, 'LF20', 'LF-8-2', 08).



% station(Nr, Name, Typ, OSM-Node-ID)

station(01, 'Hauptwache', 'BF', 4149803115).
station(02, 'Feuerwache 2', 'BF', 1607634076).
station(03, 'LZ Hüls', 'FF', 2939317437).
station(04, 'LG Traar', 'FF', 259433027).
station(05, 'LG Gellep-Stratum', 'FF', 2494720511).
station(06, 'LG Oppum', 'FF', 1476442386).
station(07, 'LZ Fischeln', 'FF', 1580072714).
station(08, 'LZ Uerdingen', 'FF', 1600010189).