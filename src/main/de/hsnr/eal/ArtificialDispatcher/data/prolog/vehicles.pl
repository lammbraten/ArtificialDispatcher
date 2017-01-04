%equipment (Id, Name, Rüstzeit, Personal)
%atna = Atemschutznotfallausrüstung
equipment(00, 'Drehleiterpark', 02, 2).
equipment(01, 'tragbare Leiter', 03, 3).
equipment(02, 'Ausrüstung zur Brandbekämpfung', 03, 4).
equipment(03, 'Rettungssatz', 02, 3).
equipment(04, 'Hebekissen', 02, 3).
equipment(05, 'Funkplatz', 01, 1).
equipment(06, 'Laufkarten', 01, 1).
equipment(07, 'Kran', 02, 2).
equipment(08, 'Atemschutznotfallausrüstung', 01, 2).

%vehicleType(Typ, [Ausrüstung], Wassertank, speed(Einsatz), speed (normal)
vehicleType('HLF-BF', [01, 01, 02, 03, 04], 2000, 60, 50).
vehicleType('HLF', [01, 01, 02, 03], 2000, 60, 50).
vehicleType('HLF-A', [01, 02, 03, 08], 2000, 60, 50). 
vehicleType('LF8', [01, 02], 600, 60, 50). 
vehicleType('LF20', [01, 02], 1600, 60, 50). 
vehicleType('ELW', [05, 06], 0, 70, 50). 
vehicleType('RW2-K', [01, 04, 07, 03], 0, 50, 40). 
vehicleType('GTLF', [02], 0, 50, 40). 
vehicleType('DLK-23', [00], 0, 50, 40). 

%vehicle(Id, Typ, Name, Station, Besatzung)
vehicle(000, 'ELW', 'ELW-1-1', 01, 2).
vehicle(001, 'HLF-BF', 'HLF-1-1', 01, 7).
vehicle(002, 'DLK-23', 'DLK-1-1', 01, 2).
vehicle(003, 'HLF-BF', 'HLF-1-2', 01, 5).
vehicle(004, 'RW2-K', 'RW-1-1', 01, 2).
vehicle(005, 'GTLF', 'GTLF-1-1', 01, 1).

vehicle(006, 'HLF-BF', 'HLF-2-1', 02, 6).
vehicle(007, 'DLK-23', 'DLK-2-1', 02, 2).

vehicle(008, 'HLF', 'HLF-3-1', 03, 9).
vehicle(009, 'HLF-A', 'HLF-3-2', 03, 8).
vehicle(010, 'HLF', 'HLF-3-3', 03, 9).
vehicle(011, 'DLK-23', 'DLK-3-1', 03, 3).

vehicle(012, 'LF8', 'LF-4-1', 04, 9).

vehicle(013, 'HLF-A', 'HLF-5-1', 05, 8).

vehicle(014, 'HLF-A', 'HLF-6-1', 06, 8).
vehicle(015, 'LF20', 'LF-6-1', 06, 9).

vehicle(016, 'HLF-A', 'HLF-7-1', 07, 8).
vehicle(017, 'LF20', 'LF-7-1', 07, 9).

vehicle(018, 'LF8', 'LF-8-1', 08, 9).
vehicle(019, 'LF20', 'LF-8-2', 08, 9).



% station(Nr, Name, Typ, OSM-Node-ID)

station(01, 'Hauptwache', 'BF', 4149803115).
station(02, 'Feuerwache 2', 'BF', 1607634076).
station(03, 'LZ Hüls', 'FF', 2939317437).
station(04, 'LG Traar', 'FF', 259433027).
station(05, 'LG Gellep-Stratum', 'FF', 2494720511).
station(06, 'LG Oppum', 'FF', 1476442386).
station(07, 'LZ Fischeln', 'FF', 1580072714).
station(08, 'LZ Uerdingen', 'FF', 1600010189).