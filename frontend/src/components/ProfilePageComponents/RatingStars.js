import { FaStar, FaRegStar, FaStarHalfAlt } from 'react-icons/fa';

const RatingStars = ({ rating }) => {
    const totalStars = 5;
    let fullStars = Math.floor(rating);
    let halfStar = rating % 1 >= 0.5 ? 1 : 0;
    let emptyStars = totalStars - fullStars - halfStar;

    return (
        <>
            {Array(fullStars)
                .fill()
                .map((_, i) => (
                    <FaStar key={`full-${i}`} color="gold" />
                ))}
            {halfStar === 1 && <FaStarHalfAlt key="half" color="gold" />}
            {Array(emptyStars)
                .fill()
                .map((_, i) => (
                    <FaRegStar key={`empty-${i}`} color="gray.300" />
                ))}
        </>
    );
};

export default RatingStars;
