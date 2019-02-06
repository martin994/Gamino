# Gamino
Gamino: Red social para videojugadores , la mejor de habla hispana.

Nomenclatura de componentes:

 Componentes xml: [NombrePlantalla] _ [TipoDeComponenteAbreviado] _ [AccionDeComponente] 
 
 Objetos Java:[TipoDeComponenteAbreviado]_[AccionDeComponente]

Nota: en el caso de usar un componente que no este entre las abreviaturas, agregarlo al README.

Abreviaturas de componentes.

Button > btn 

TextView > tV 

EditText > edt 

Switch > sw 

ListView > lV 

Image > img 

ToggleButton > tg 

Spinner > sp 

RadioButton > rBtn 

RadioGoup > rGr 

Ejemplo: 
Componenete xml activity_alta_publicacion_sp_intereses

Componente Java: sp_intereses

Nomenclatura de mensajes de commit y merge:

Titulo:  [FuncionalidadTrabajada]:  [Commit/Merge]  [Estado];  [Fecha en dd/mm/aa]


Dependencias para firebas

    implementation 'com.google.firebase:firebase-core:16.0.4'
    implementation 'com.google.firebase:firebase-auth:16.0.4'


    implementation 'com.google.firebase:firebase-database:16.0.5'
    implementation 'com.google.firebase:firebase-storage:16.0.3'
    implementation 'com.google.firebase:firebase-auth:16.0.3'

    implementation 'com.github.bumptech.glide:glide:4.8.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.8.0'
