# AsyncBitmapLoader
다이나믹 비트맵 로딩용 라이브러리.
메모리 캐시를 허용된 메모리의 1/8을 할당하고, 디스크 캐시는 사용하지 않는다.


Usage
===============

### build.gradle

```
repositories {
    maven { url 'http://junsuk5.github.io/AsyncBitmapLoader/repository' }
}

dependencies {
    compile 'com.suwonsmartapp:async-bitmap-loader:0.2'
}
```

### code

```
// 선언
private AsyncBitmapLoader mAsyncBitmapLoader;

...

// onCreate 등에서 객체 생성
mAsyncBitmapLoader = new AsyncBitmapLoader(context);

// setBitmapLoadListener 를 구현
mAsyncBitmapLoader.setBitmapLoadListener(new AsyncBitmapLoader.BitmapLoadListener() {
    @Override
    public Bitmap getBitmap(int position) {
        // ListView 등의 position 에 표시할 Bitmap을 정의하여 리턴
        return bitmap;
    }
});
        
...

// 이미지 호출
mAsyncBitmapLoader.loadBitmap(position, imageView);

```


LICENSE
===============

```
Copyright 2015 Junsuk Oh

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
