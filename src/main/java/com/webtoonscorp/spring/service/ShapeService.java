package com.webtoonscorp.spring.service;

import com.webtoonscorp.spring.domain.Shape;
import com.webtoonscorp.spring.repository.ShapeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.plugin.dom.exception.InvalidStateException;

@Service
public class ShapeService {

    @Autowired
    private ShapeRepository shapeRepository;

    public Shape get(int point, boolean hasEqualAdjacentSides) {

        if (point < 3) {
            throw new InvalidStateException("도형에 필요한 점은 최소 3개 이상입니다.");
        }

        if (point == 3) {
            return shapeRepository.getTriangle();
        }
        else if (point == 4) {
            if (hasEqualAdjacentSides) {
                return shapeRepository.getSquare();
            }
            else {
                return shapeRepository.getRectangle();
            }
        }
        else {
            return shapeRepository.generate(point);
        }
    }
}
