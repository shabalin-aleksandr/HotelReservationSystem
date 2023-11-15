import { FaStar, FaRegStar, FaStarHalfAlt } from 'react-icons/fa';

const RatingStars = ({ rating }) => {
    const validRating = typeof rating === 'number' && rating >= 0 && rating <= 5 ? rating : 0;
    const fullStars = Math.floor(validRating);
    const halfStar = validRating % 1 >= 0.5 ? 1 : 0;
    const emptyStars = 5 - fullStars - halfStar;

    const starElements = [];

    for (let i = 0; i < fullStars; i++) {
        starElements.push(<FaStar key={`full-${i}`} color="gold" />);
    }

    if (halfStar) {
        starElements.push(<FaStarHalfAlt key="half" color="gold" />);
    }

    for (let i = 0; i < emptyStars; i++) {
        starElements.push(<FaRegStar key={`empty-${i}`} color="gray.300" />);
    }

    return <>{starElements}</>;
};

export default RatingStars;
