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
task(06, 'P-klemmt im Fahrzeug', [[03]], 7);
task(07, 'P-klemmt unter Fahrzeug/Objekt', [[03],[07]], 3);
task(08, 'Klein-Brandbekämpfung', [[02, 09]], 6).
task(09, 'Brandbekämpfung', [[02, 09, 09]], 9).
task(10, 'Groß-Brandbekämpfung', [[02, 09, 09]], 20).
task(11, 'Brandbekämpfung über Drehleiter', [[02, 09, 00]], 10).
task(12, 'Türöffnung', [[10],[00],[01], 5).

%
% PRIORITÄT einführen.
%

%eventType(CodeWordId, Name, [TaskIds]) Einsatztypen
%Brände
eventType(00, 'Brennt Mülleimer', [08]).
eventType(00, 'Brennt Hecke', [08]).
eventType(01, 'Brennt Baustellenklo', [08]).
eventType(01, 'Küchenbrand', [08]).
eventType(01, 'Zimmerbrand', [09]).
eventType(02, 'Zimmerbrand - P-Vermisst', [09, 04]).
eventType(02, 'Zimmerbrand - P-Fenster', [09, 03, 01]).
eventType(02, 'Wohnungsbrand', [09, 09]).
eventType(02, 'Wohnungsbrand - P-Vermisst', [09, 09, 04]).
eventType(02, 'Wohnungsbrand - P-Fenster', [09, 09, 01]).
eventType(02, 'Kellerbrand', [10]).
eventType(03, 'Kellerbrand - P-Vermisst', [10, 04]).
eventType(02, 'Kellerbrand - P-Fenster ', [10, 01]).
eventType(03, 'Dachstuhlbrand', [10, 11]).
eventType(03, 'Dachstuhlbrand - P-Vermisst', [10, 11, 04]).
eventType(03, 'Dachstuhlbrand - P-Fenster', [10, 11, 02]).
eventType(03, 'Lagerhallenbrand', [10, 10, 11, 11]).
eventType(04, 'Lagerhallenbrand - P-Vermisst', [10, 10, 11, 11, 04]).
eventType(04, 'Lagerhallenbrand - Ausgedehnt', [10, 10, 10, 11, 11]).
GasExplosion
eventType(02, 'BMA', [00]).

%Technische Hilfe
eventType(05, 'Türöffnung', [12]).
eventType(05, 'Katze auf Baum', [01]).
eventType(05, 'Türöffnung', [12]).
Bauunfall
Aufzug
Schachtrettung

%aao(CodeWordId, [VehicleTypes])