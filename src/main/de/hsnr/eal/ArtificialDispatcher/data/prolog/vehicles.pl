%equipment (Id, Name, Rüstzeit, Personal)
%atna = Atemschutznotfallausrüstung
equipment(00, 'Drehleiterpark', 02, 2).
equipment(01, 'tragbare Leiter', 03, 3).
equipment(02, 'Ausrüstung zur Brandbekämpfung', 03, 2).
equipment(03, 'Rettungssatz', 02, 3).
equipment(04, 'Hebekissen', 02, 3).
equipment(05, 'Funkplatz', 01, 1).
equipment(06, 'Laufkarten', 01, 1).
equipment(07, 'Kran', 02, 2).
equipment(08, 'Atemschutznotfallausrüstung', 01, 2).
equipment(09, 'Atemschutzgerät', 01, 0).
equipment(10, 'Zieh Fix', 01, 1).

%vehicleType(Typ, [Ausrüstung], Wassertank, speed(Einsatz), speed (normal)
vehicleType('HLF-BF', [01, 01, 02, 03, 04, 09, 09, 09, 09, 10], 2000, 60, 50).
vehicleType('HLF', [01, 01, 01, 02, 03, 09, 09, 09, 09], 2000, 60, 50).
vehicleType('HLF-A', [01, 01, 02, 03, 08, 09, 09, 09, 09, 09, 09, 10], 2000, 60, 50). 
vehicleType('LF8', [01, 02, 09, 09, 09, 09], 600, 60, 50). 
vehicleType('LF20', [01, 01, 02, 02, 09, 09, 09, 09], 1600, 60, 50). 
vehicleType('ELW', [05, 06], 0, 70, 50). 
vehicleType('RW2-K', [01, 04, 07, 03, 10], 0, 50, 40). 
vehicleType('GTLF', [02, 02, 09, 09], 0, 50, 40). 
vehicleType('DLK-23', [00, 09, 09], 0, 50, 40). 

%vehicle(Id, Typ, Name, Wache, Besatzung)
vehicle(000, 'ELW', 'ELW-1-1', 01, 2).
vehicle(001, 'HLF-BF', 'HLF-1-1', 01, 7).
vehicle(002, 'DLK-23', 'DLK-1-1', 01, 2).
vehicle(003, 'HLF-BF', 'HLF-1-2', 01, 5).
vehicle(004, 'RW2-K', 'RW-1-1', 01, 2).
vehicle(005, 'GTLF', 'GTLF-1-1', 01, 2).

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



% station(Nr, Name, Typ, OSM-Node-ID) (wache)

station(01, 'Hauptwache', 'BF', 4149803115).
station(02, 'Feuerwache 2', 'BF', 1607634076).
station(03, 'LZ Hüls', 'FF', 2939317437).
station(04, 'LG Traar', 'FF', 259433027).
station(05, 'LG Gellep-Stratum', 'FF', 2494720511).
station(06, 'LG Oppum', 'FF', 1476442386).
station(07, 'LZ Fischeln', 'FF', 1580072714).
station(08, 'LZ Uerdingen', 'FF', 1600010189).

%codeWord(Id, Name) Stichwort
codeWord(00, 'B-Klein').
codeWord(01, 'B1').
codeWord(02, 'B2').
codeWord(03, 'B3').
codeWord(04, 'B4').

codeWord(05, 'TH-Klein').
codeWord(06, 'TH1').
codeWord(07, 'TH2').
codeWord(08, 'TH3').

%task(Id, Name, [[EquipmentIds],[AlternativeEquipmentIds]], EstimatedTime) Aufgabe
task(00, 'Erkunden', [[01],[]], 1). 
task(01, 'Personenrettung über Leiter', [[01],[00]], 2). 
task(02, 'Personenrettung über Drehleiter', [[00]], 1). 
task(03, 'Personenrettung über Treppenhaus', [[02, 09, 09]], 2). 
task(04, 'Personenrettung P-Vermisst', [[02, 09, 09]], 6). 
task(05, 'Atemschutznotfall', [[02, 08, 09, 09], [02, 09, 09, 09, 09]], 6). 
task(06, 'P-klemmt im Fahrzeug', [[03]], 7).
task(07, 'P-klemmt unter Fahrzeug/Objekt', [[03],[07]], 3).
task(08, 'Klein-Brandbekämpfung', [[02, 09]], 6).
task(09, 'Brandbekämpfung', [[02, 09, 09]], 9).
task(10, 'Groß-Brandbekämpfung', [[02, 09, 09]], 20).
task(11, 'Brandbekämpfung über Drehleiter', [[02, 09, 00]], 10).
task(12, 'Türöffnung', [[10],[00]], 5).

%
% PRIORITÄT einführen. ?
%

%emergencyType(CodeWordId, Name, [TaskIds]) Einsatztypen
%Brände
emergencyType(00, 'Brennt Mülleimer', [08]).
emergencyType(00, 'Brennt Hecke', [08]).
emergencyType(01, 'Brennt Baustellenklo', [08]).
emergencyType(01, 'Küchenbrand', [08]).
emergencyType(01, 'Zimmerbrand', [09]).
emergencyType(02, 'BMA', [00]).
emergencyType(02, 'Zimmerbrand - P-Vermisst', [09, 04]).
emergencyType(02, 'Zimmerbrand - P-Fenster', [09, 03, 01]).
emergencyType(02, 'Wohnungsbrand', [09, 09]).
emergencyType(02, 'Wohnungsbrand - P-Vermisst', [09, 09, 04]).
emergencyType(02, 'Wohnungsbrand - P-Fenster', [09, 09, 01]).
emergencyType(02, 'Kellerbrand', [10, 00]).
emergencyType(03, 'Kellerbrand - P-Vermisst', [10, 04, 00]).
emergencyType(02, 'Kellerbrand - P-Fenster ', [10, 01, 00]).
emergencyType(03, 'Dachstuhlbrand', [10, 11, 00]).
emergencyType(03, 'Dachstuhlbrand - P-Vermisst', [10, 11, 04, 00]).
emergencyType(03, 'Dachstuhlbrand - P-Fenster', [10, 11, 02, 00]).
emergencyType(03, 'Lagerhallenbrand', [10, 10, 11, 11, 00]).
emergencyType(04, 'Lagerhallenbrand - P-Vermisst', [10, 10, 11, 11, 04, 00]).
emergencyType(04, 'Lagerhallenbrand - Ausgedehnt', [10, 10, 10, 11, 11, 00]).
emergencyType(04, 'GasExplosion', [10, 04, 04, 11, 00]).

%Technische Hilfe
emergencyType(05, 'Türöffnung', [12]).
emergencyType(05, 'Katze auf Baum', [01]).
emergencyType(05, 'Türöffnung', [12]).
emergencyType(05, 'Aufzug', [12]).
emergencyType(06, 'Bauunfall', [07, 01]).
emergencyType(06, 'Schachtrettung' , [01]).
emergencyType(06, 'VU - Klein', [08]).
emergencyType(07, 'VU - P-klemmt', [06, 08]).
emergencyType(07, 'VU 2 - P-klemmt', [06, 06, 08]).
emergencyType(07, 'VU 3 - P-klemmt', [06, 06, 07, 08]).

%aao(CodeWordId, [VehicleTypes])