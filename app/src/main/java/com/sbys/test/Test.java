package com.sbys.test;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Test {

    public static void main(String[] args){
//        map();
//        zip1();
//            zip2();
//        contact();
        concatMap();

    }

    private static void concatMap(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list=new ArrayList<>();
                for(int i=0;i<3;i++){
                    list.add("I am value::"+integer);
                }

                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

    }

    private static void contact(){
        Observable<String> stringObservable=Observable.just("a","b","c");
        Observable<Integer> integerObservable1=Observable.just(1,2,3,4,5);
        Observable<Integer> integerObservable2=Observable.just(6,7,8);
        Observable.concat(integerObservable1,integerObservable2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }

    private static void zip2(){
        Observable<String> stringObservable=Observable.just("a","b","c");
        Observable<Integer> integerObservable=Observable.just(1,2,3,4,5);
        Observable.zip(stringObservable, integerObservable, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s+"======"+integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }


    private static void zip1() {
        List<Observable<Integer>> observableList=new ArrayList<>();
        Observable<Integer> one=Observable.just(1);
        Observable<Integer> two=Observable.just(2);
        Observable<Integer> three=Observable.just(3);
        observableList.add(one);
        observableList.add(two);
        observableList.add(three);
        
        Observable.zip(observableList, new Function<Object[], String[]>() {
            @Override
            public String[] apply(Object[] objects) throws Exception {
                String[] strs=new String[objects.length];
                for(int i=0;i<strs.length;i++){
                    strs[i]="this is"+objects[i];
                }
                return strs;
            }
        }).subscribe(new Consumer<String[]>() {
            @Override
            public void accept(String[] strings) throws Exception {
                for(String str:strings){
                    System.out.println(str);
                }
            }
        });


    }

    private static void map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer*2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("map后的结果:"+integer);
            }
        });
    }
}
