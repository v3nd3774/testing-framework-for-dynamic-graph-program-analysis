package io.github.v3nd3774.uta2218.cse6324.generate;


import io.github.v3nd3774.uta2218.cse6324.s001.HashMapEdge;
import lombok.Data;
import org.apache.commons.text.CaseUtils;
import org.jgrapht.Graph;
import org.jgrapht.GraphIterables;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

public class GraphMapper {

    public static final String TYPE = "TYPE";
    public static final String NAME = "name";

    public <T> List<T> parseValue(Graph<HashMap<String, String>, HashMapEdge> graph, Class<T> tClass) throws IntrospectionException {
        List<T> tList = new ArrayList<>();
        Map<String, RelationshipMap> relationshipMap = buildRelationshipMap(tClass);
        GraphIterables<HashMap<String, String>, HashMapEdge> graphIterables = graph.iterables();
        graphIterables.vertices().forEach(vertices -> {
            Iterable<HashMapEdge> hashMapEdges = graphIterables.edgesOf(vertices);
            hashMapEdges.forEach(hashMapEdge -> {
                if (null != hashMapEdge.getSource()) {
                    if(CaseUtils.toCamelCase(tClass.getSimpleName(),false).equals(hashMapEdge.getSource().get(TYPE))){
                        String name = hashMapEdge.getSource().get(NAME);
                        T t = tList.stream().filter(t1 -> name.equals(getName(tClass, t1))).findFirst().orElseGet(() -> {
                                T t1 = newInstance(tClass, name);
                                tList.add(t1);
                                return t1;
                            });
                        relationshipExtractor(relationshipMap, hashMapEdge, t);
                    }
                }
            });
        });
        return tList;
    }

    private <T> void relationshipExtractor(Map<String, RelationshipMap> relationshipMap, HashMapEdge hashMapEdge, T t) {
        RelationshipMap relationshipMap1 = relationshipMap.get(hashMapEdge.getLabel().get(TYPE));
        try {
            if (null != relationshipMap1) {
                Object instance = relationshipMap1.getRelationshipClass().getConstructor().newInstance();
                for (Field field : instance.getClass().getDeclaredFields()) {
                    String name = field.getName();
                    if (!field.isAnnotationPresent(TargetNode.class)) {
                        String str = hashMapEdge.getLabel().get(name.toUpperCase());
                        if (null != str) {
                            setAttribute(field, relationshipMap1.getRelationshipClass(), instance, str);
                        }
                    } else {
                        Class<?> actualTypeArgument = field.getType();
                        Object obj = newInstance(actualTypeArgument, hashMapEdge.getTarget().get(NAME));
                        new PropertyDescriptor(name, relationshipMap1.getRelationshipClass()).getWriteMethod().invoke(instance, obj);
                    }
                }
                relationshipMap1.getMethod().invoke(t, Collections.singleton(instance));
            }
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException | NoSuchMethodException | InstantiationException e){
            e.printStackTrace();
        }
    }

    private void setAttribute(Field field, Class<?> relationshipClass, Object instance, String str) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        if(field.getType().equals(String.class)){
            new PropertyDescriptor(field.getName(), relationshipClass).getWriteMethod().invoke(instance, str);
        } else if (field.getType().equals(BigDecimal.class)){
            new PropertyDescriptor(field.getName(), relationshipClass).getWriteMethod().invoke(instance, new BigDecimal(str));
        } else if (isInt(str)){
            new PropertyDescriptor(field.getName(), relationshipClass).getWriteMethod().invoke(instance, Integer.parseInt(str));
        }

    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    private <T> Map<String, RelationshipMap> buildRelationshipMap(Class<T> tClass) throws IntrospectionException {
        Map<String, RelationshipMap> relationships = new HashMap<>();
        for (Field field : tClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Relationship.class)) {
                Relationship relationship = field.getAnnotation(Relationship.class);
                String value = relationship.value();
                ParameterizedType fieldType = (ParameterizedType) field.getGenericType();
                Class<?> actualTypeArgument = (Class<?>) fieldType.getActualTypeArguments()[0];
                RelationshipMap relationshipMap = new RelationshipMap();
                relationshipMap.setValue(value);
                relationshipMap.setRelationshipClass(actualTypeArgument);
                relationshipMap.setField(field);
                relationshipMap.setMethod(new PropertyDescriptor(field.getName(), tClass).getWriteMethod());
                relationships.put(value, relationshipMap);
            }
        }
        return relationships;
    }

    private <T> T newInstance(Class<T> tClass, String name) {
        Constructor<T> constructor = null;
        try {
            constructor = tClass.getConstructor(String.class);
            return constructor.newInstance(name);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }

    }

    private <T> String getName(Class<T> tClass, T t){
        try {
            return (String) new PropertyDescriptor(NAME, tClass).getReadMethod().invoke(t);
        } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
            e.printStackTrace();
            return "";
        }
    }
}

@Data
class RelationshipMap {
    String value;
    Class<?> relationshipClass;
    Field field;
    Method method;
}
