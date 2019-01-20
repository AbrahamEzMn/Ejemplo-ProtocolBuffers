# Protocol-Buffers con PHP

Versión
* Os: Ubuntu 18.04.1 LTS
* Cpu: Intel(R) Core(TM) i3-3120M @ 2.50GHz
* Protobuf: libprotoc 3.6.1
* Sintaxis: Proto3
* PHP: 7.2.10
* Composer: 1.6.5

## Inicializando el proyecto

Para usar la libreria de `google/protobuf` necesitaremos usar el administrador de dependencias [composer](https://getcomposer.org/download/) y ejecutar el siguiente comando:
```
$ composer init
```

Despues agregaremos la dependencia de el proyecto de `google/protobuf` en el archivo `composer.json` que se generó al terminar de ejecutar el comando `init` y agregaremos la siguiente linea en los corchetes del `require` :
```
"google/protobuf":"*"
```

Y entonces instalaremos ejecutamos el siguiente comando para instalar 
```
$ composer update
```


```
"autoload": {
    "psr-4": {
        "Messages\\":"./proto/Messages/",
        "GPBMetadata\\":"./proto/GPBMetadata/"
    }
}
```

```
$ composer dump-autoload -o
```

## Referencias
[https://github.com/protocolbuffers/protobuf/tree/master/php](https://github.com/protocolbuffers/protobuf/tree/master/php)<br />
[https://developers.google.com/protocol-buffers/docs/reference/php-generated](https://developers.google.com/protocol-buffers/docs/reference/php-generated)<br />
(https://getcomposer.org/doc/01-basic-usage.md#autoloading)[https://getcomposer.org/doc/01-basic-usage.md#autoloading] <br />