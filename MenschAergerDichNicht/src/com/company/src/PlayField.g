grammar PlayField;
file  : row* EOF ;
row   : value (Separator value)* (LineBreak | EOF) ;
value : SimpleValue | WayValue | StartValue | GoalValue | ToGoalValue | HomeValue ;

Separator   : ';' ;
LineBreak   : '\r'?'\n' | '\r';

SimpleValue : 'NO' ;
WayValue    : 'W'('N'|'S'|'W'|'E') ;
StartValue  : 'S'('1'|'2'|'3'|'4')('N'|'S'|'W'|'E') ;
GoalValue   : 'G'('1'|'2'|'3'|'4')('N'|'S'|'W'|'E')* ;
ToGoalValue : 'W'('N'|'S'|'W'|'E')'G'('N'|'S'|'W'|'E') ;
HomeValue   : 'H'('1'|'2'|'3'|'4') ;





